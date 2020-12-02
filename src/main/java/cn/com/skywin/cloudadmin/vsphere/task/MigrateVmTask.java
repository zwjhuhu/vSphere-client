package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.List;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfDatastoreHostMount;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.ArrayOfVirtualDevice;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostMountInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceFileBackingInfo;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineMovePriority;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineSummary;

import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto;
import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto.CreatedDiskInfoDto;
import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto.CreatedNetcardInfoDto;

/**
 * 
 * @author zwj
 * @date 2020/12/02
 */
public class MigrateVmTask extends BaseTask<CreatedOrMigratedVmInfoDto> {

    /**
     * vm标识
     */
    private String vm = "";

    /**
     * 目标物理机标识
     */
    private String host = "";

    /**
     * 允许使用使用的datastore的标识列表，如果不传，则使用与原来虚机相同的存储<br/>
     * 这里用列表，是因为如果数据不同步，vcenter自己可能找到没有同步到平台中的存储
     */
    private List<String> availDatastores;

    private ManagedObjectReference hostref = null;

    private ManagedObjectReference dsmor = null;

    private ManagedObjectReference vmref = null;

    private CreatedOrMigratedVmInfoDto migratedInfo = new CreatedOrMigratedVmInfoDto();

    public MigrateVmTask(VsphereClient client, String vm, String host, List<String> availDatastores) {
        super(client);
        this.vm = vm;
        this.host = host;
        if (availDatastores == null) {
            this.availDatastores = new ArrayList<>(0);
        } else {
            // 排重
            List<String> nlist = new ArrayList<>();
            for (String n : availDatastores) {
                if (!nlist.contains(n)) {
                    nlist.add(n);
                }
            }
            this.availDatastores = nlist;
        }

    }

    @Override
    public CreatedOrMigratedVmInfoDto doTask() throws Exception {
        customValidation();
        migrateVM();
        fillResult();
        return migratedInfo;
    }

    private void customValidation() throws Exception {

        vmref = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (vmref == null) {
            throw new IllegalArgumentException("Specified VM Instance [" + vm + "] was not found");
        }

        if (host == null) {
            throw new IllegalArgumentException("Need specified host");
        }
        hostref = findTypeReferenceById(TYPE_HOSTSYSTEM, host);
        if (hostref == null) {
            throw new IllegalArgumentException("Specified host [" + host + "] was not found");
        }

        ManagedObjectReference vmHost =
            (ManagedObjectReference)getMOREFs.entityProps(vmref, new String[] {"runtime.host"}).get("runtime.host");
        if (host.equals(vmHost.getValue())) {
            throw new IllegalArgumentException("Need specified different host");
        }

        if (!availDatastores.isEmpty()) {
            List<ManagedObjectReference> oridsrefs = ((ArrayOfManagedObjectReference)getMOREFs
                .entityProps(vmref, new String[] {"datastore"}).get("datastore")).getManagedObjectReference();
            for (ManagedObjectReference ref : oridsrefs) {
                if (availDatastores.contains(ref.getValue())) {
                    dsmor = ref;
                    break;
                }
            }
            // 候选的数据存储和原来虚机的存储不一致
            if (dsmor == null) {
                VirtualMachineSummary summary =
                    (VirtualMachineSummary)getMOREFs.entityProps(vmref, new String[] {"summary"}).get("summary");
                long needCapacity = summary.getStorage().getUncommitted();
                findDatastoreWithFreeSpace(needCapacity);
                if (dsmor == null) {
                    throw new IllegalArgumentException("No datastore can fullfill host [" + host + "]  "
                        + "and free capacity larger than [" + needCapacity + "] Bytes");
                }
            }
        }

    }

    private boolean checkHostAndDatastoreMounted(ManagedObjectReference dsmr, ManagedObjectReference hostmr)
        throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        final List<DatastoreHostMount> hostmounts =
            ((ArrayOfDatastoreHostMount)getMOREFs.entityProps(dsmr, new String[] {"host"}).get("host"))
                .getDatastoreHostMount();

        for (DatastoreHostMount datastoreHostMount : hostmounts) {
            if (datastoreHostMount == null) {
                continue;
            }
            HostMountInfo mountInfo = datastoreHostMount.getMountInfo();
            if (mountInfo == null) {
                continue;
            }

            final Boolean accessible = mountInfo.isAccessible();
            // the values "accessible" and "mounted" need not be set by the server.
            final Boolean mounted = mountInfo.isMounted();
            // if mounted is not set, assume it is true
            if ((accessible != null && accessible) && (mounted == null || mounted)) {
                if (datastoreHostMount.getKey().getValue().equals(hostmr.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 从需要的容量选择存储
     * 
     * @param minFreeSpace
     * @return
     * @throws Exception
     */
    private void findDatastoreWithFreeSpace(long minFreeSpace) throws Exception {
        List<ManagedObjectReference> datastores =
            ((ArrayOfManagedObjectReference)getMOREFs.entityProps(hostref, new String[] {"datastore"}).get("datastore"))
                .getManagedObjectReference();

        for (ManagedObjectReference ref : datastores) {
            DatastoreSummary ds = (DatastoreSummary)getMOREFs.entityProps(ref, new String[] {"summary"}).get("summary");
            if (availDatastores.contains(ref.getValue()) && ds.getFreeSpace() > minFreeSpace
                && checkHostAndDatastoreMounted(ref, hostref)) {
                dsmor = ref;
                return;
            }
        }
    }

    private void migrateVM() throws Exception {

        VirtualMachineRelocateSpec spec = new VirtualMachineRelocateSpec();
        if (dsmor != null) {
            spec.setDatastore(dsmor);
        }
        spec.setHost(hostref);

        ManagedObjectReference cloneTask =
            vimPort.relocateVMTask(vmref, spec, VirtualMachineMovePriority.DEFAULT_PRIORITY);
        if (!getTaskResultAfterDone(cloneTask)) {
            throw new IllegalStateException("Failure Migrate VM Instance [" + vm + "] to host [" + host + "]");
        }
    }

    private void fillResult() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        vmref = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        migratedInfo.setVm(vmref.getValue());
        List<CreatedDiskInfoDto> disks = new ArrayList<>();
        migratedInfo.setDisks(disks);
        List<CreatedNetcardInfoDto> nics = new ArrayList<>();
        migratedInfo.setNics(nics);

        List<VirtualDevice> listvd =
            ((ArrayOfVirtualDevice)getMOREFs.entityProps(vmref, new String[] {"config.hardware.device"})
                .get("config.hardware.device")).getVirtualDevice();

        for (VirtualDevice dev : listvd) {
            if (dev instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk)dev;
                VirtualDeviceBackingInfo bak = disk.getBacking();
                if (bak instanceof VirtualDeviceFileBackingInfo) {
                    VirtualDeviceFileBackingInfo filebak = (VirtualDeviceFileBackingInfo)bak;
                    CreatedDiskInfoDto dto = new CreatedDiskInfoDto();
                    dto.setKey(disk.getKey());
                    dto.setDatastore(filebak.getDatastore().getValue());
                    dto.setCapacity(disk.getCapacityInKB() * 1024L);
                    dto.setPath(filebak.getFileName());
                    disks.add(dto);
                }

            } else if (dev instanceof VirtualEthernetCard) {
                VirtualEthernetCard netcard = (VirtualEthernetCard)dev;
                VirtualDeviceBackingInfo bak = netcard.getBacking();
                if (bak instanceof VirtualEthernetCardNetworkBackingInfo) {
                    VirtualEthernetCardNetworkBackingInfo nicbak = (VirtualEthernetCardNetworkBackingInfo)bak;
                    CreatedNetcardInfoDto dto = new CreatedNetcardInfoDto();
                    dto.setLabel(netcard.getDeviceInfo().getLabel());
                    dto.setMac(netcard.getMacAddress());
                    dto.setNetwork(nicbak.getNetwork().getValue());
                    nics.add(dto);
                }
            }
        }
    }
}
