package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfDatastoreHostMount;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.ArrayOfVirtualDevice;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostMountInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceFileBackingInfo;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineStorageSummary;
import com.vmware.vim25.VirtualPCNet32;
import com.vmware.vim25.VirtualSCSIController;

import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto;
import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto.CreatedDiskInfoDto;
import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto.CreatedNetcardInfoDto;
import cn.com.skywin.cloudadmin.commons.UUIDGenerator;

/**
 * 通过vcenter中的虚机模板，创建新的虚机，因为博士给的接口在创建云路由的时候异常难用，自己实现<br/>
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class CreateVmFromImageTask extends BaseTask<CreatedOrMigratedVmInfoDto> {

    /**
     * 克隆之后虚机的名称
     */
    private String cloneName;

    /**
     * 模板在vcenter中的vm标识
     */
    private String imagevm = "";

    /**
     * 数据中心标识
     */
    private String datacenter;

    /**
     * 这里用列表，是因为如果数据不同步，vcenter自己可能找到没有同步到平台中的数据存储
     */
    private List<String> availDatastores;

    /**
     * 这里用列表，是因为如果数据不同步，vcenter自己可能找到没有同步到平台中的物理机
     */
    private List<String> availHosts;

    /**
     * 需要挂载的网络
     */
    private List<String> networks;

    /**
     * 创建一个数据云盘的容量，单位Byte，不创建数据云盘就填0L;
     */
    private long dataDiskSize;

    /**
     * 创建数据云盘的uuid
     */
    private String dataDiskUUID;
    
    /**
     * 在创建之后是否打开电源
     */
    private boolean startAfterCreate = true;

    private int vncport;

    private Map<String, String> netNameMap = new HashMap<>();

    private ManagedObjectReference dcmor = null;

    private ManagedObjectReference dsmor = null;

    private String selDatastoreName = null;

    private ManagedObjectReference imageVmref = null;

    private ManagedObjectReference clonedVmRef = null;

    private ManagedObjectReference hostmr = null;

    private CreatedOrMigratedVmInfoDto createdInfo = new CreatedOrMigratedVmInfoDto();

    public CreateVmFromImageTask(VsphereClient client, String imagevm, String cloneName, String datacenter,
        List<String> availDatastores, List<String> availHosts, List<String> networks, long dataDiskSize,
        String dataDiskUUID, int vncport,boolean startAfterCreate) {
        super(client);
        this.imagevm = imagevm;
        this.cloneName = cloneName;
        this.datacenter = datacenter;
        this.availDatastores = availDatastores;
        this.availHosts = availHosts;
        this.networks = networks;
        this.dataDiskSize = dataDiskSize;
        this.dataDiskUUID = dataDiskUUID;
        this.vncport = vncport;
        this.startAfterCreate  = startAfterCreate;
    }

    @Override
    public CreatedOrMigratedVmInfoDto doTask() throws Exception {
        customValidation();
        cloneVM();
        fillResult();
        return createdInfo;
    }

    private void customValidation() throws Exception {

        imageVmref = findTypeReferenceById(TYPE_VIRTUALMACHINE, imagevm);
        if (imageVmref == null) {
            throw new IllegalArgumentException("Specified VM Image [" + imageVmref + "] was not found");
        }
        if (vncport < 5900) {
            throw new IllegalArgumentException("vnc port number " + vncport + " invalid");
        }
        if (datacenter != null && datacenter.length() != 0) {
            dcmor = findTypeReferenceById(TYPE_DATACENTER, datacenter);
        }
        if (dcmor == null) {
            throw new IllegalArgumentException("Specified Datacenter [" + datacenter + "] not Found");
        }
        if (availDatastores == null || availDatastores.isEmpty()) {
            throw new IllegalArgumentException("Need specified datastores");
        }
        if (availHosts == null || availHosts.isEmpty()) {
            throw new IllegalArgumentException("Need specified hosts");
        }
        if (networks == null || networks.isEmpty()) {
            throw new IllegalArgumentException("Need specified networks");
        }
        // 排重
        List<String> nlist = new ArrayList<>();
        for (String n : networks) {
            if (!nlist.contains(n)) {
                nlist.add(n);
            }
        }
        networks = nlist;
        for (String network : networks) {
            Object[] ret = findTypeNameAndReferenceById(TYPE_NETWORK, network);
            if (ret != null) {
                netNameMap.put(network, (String)ret[0]);
            } else {
                throw new IllegalArgumentException("Specified Network [" + network + "] not Found");
            }
        }
        // 小于1M，创建不了
        if (dataDiskSize < 1024L * 1204) {
            dataDiskSize = 0L;
        }
        if (dataDiskSize > 0L && StringUtils.isBlank(dataDiskUUID)) {
            dataDiskUUID = UUIDGenerator.gen();
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

    private boolean checkNetworksInHost(ManagedObjectReference host)
        throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        List<ManagedObjectReference> nrs =
            ((ArrayOfManagedObjectReference)getMOREFs.entityProps(host, new String[] {"network"}).get("network"))
                .getManagedObjectReference();
        List<String> tmps = new ArrayList<>();
        for (ManagedObjectReference net : nrs) {
            tmps.add(net.getValue());
        }
        return tmps.containsAll(networks);
    }

    /**
     * 从需要的容量选择物理机和数据存储
     * 
     * @param minFreeSpace
     * @return
     * @throws Exception
     */
    private void findHostAndDatastoreNameWithFreeSpace(long minFreeSpace) throws Exception {
        Map<String, List<ManagedObjectReference>> dsmap = new HashMap<>();
        List<ManagedObjectReference> datastores = ((ArrayOfManagedObjectReference)getMOREFs
            .entityProps(imageVmref, new String[] {"datastore"}).get("datastore")).getManagedObjectReference();
        ManagedObjectReference imgdsmr = datastores.get(0);

        List<ManagedObjectReference> hostmrs = new ArrayList<>();

        final List<DatastoreHostMount> hostmounts =
            ((ArrayOfDatastoreHostMount)getMOREFs.entityProps(imgdsmr, new String[] {"host"}).get("host"))
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
                ManagedObjectReference hmr = datastoreHostMount.getKey();
                if (availHosts.contains(hmr.getValue()) && checkNetworksInHost(hmr)) {
                    hostmrs.add(hmr);
                }
            }
        }

        dsmor = null;
        List<ManagedObjectReference> selHosts = new ArrayList<>();
        for (ManagedObjectReference hostref : hostmrs) {
            String key = hostref.getValue();
            datastores = ((ArrayOfManagedObjectReference)getMOREFs.entityProps(hostref, new String[] {"datastore"})
                .get("datastore")).getManagedObjectReference();

            for (ManagedObjectReference ref : datastores) {
                DatastoreSummary ds =
                    (DatastoreSummary)getMOREFs.entityProps(ref, new String[] {"summary"}).get("summary");
                if (availDatastores.contains(ref.getValue()) && ds.getFreeSpace() > minFreeSpace
                    && checkHostAndDatastoreMounted(ref, hostref)) {
                    selHosts.add(hostref);
                    List<ManagedObjectReference> tmp = dsmap.computeIfAbsent(key, k -> new ArrayList<>());
                    tmp.add(ref);
                }
            }
        }

        if (selHosts.isEmpty()) {
            throw new IllegalArgumentException("No hosts could be found for creating VM from image");
        }

        if (selHosts.size() == 1) {
            hostmr = selHosts.get(0);
        } else {
            int index = ThreadLocalRandom.current().nextInt(selHosts.size());
            hostmr = selHosts.get(index);
        }
        List<ManagedObjectReference> tmp = dsmap.get(hostmr.getValue());
        if (tmp.size() == 1) {
            dsmor = tmp.get(0);
        } else {
            int index = ThreadLocalRandom.current().nextInt(tmp.size());
            dsmor = tmp.get(index);
        }
        if (dsmor == null) {
            throw new IllegalArgumentException("No datastore could be found for creating VM from image");
        }
        selDatastoreName = (String)getMOREFs.entityProps(dsmor, new String[] {"summary.name"}).get("summary.name");
    }

    private long findImageCapacity() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        VirtualMachineStorageSummary summary = (VirtualMachineStorageSummary)getMOREFs
            .entityProps(imageVmref, new String[] {"summary.storage"}).get("summary.storage");
        return summary.getUncommitted();
    }

    private List<Integer> getControllerKey(ManagedObjectReference vmMor)
        throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        List<Integer> retVal = new ArrayList<Integer>();

        List<VirtualDevice> listvd =
            ((ArrayOfVirtualDevice)getMOREFs.entityProps(vmMor, new String[] {"config.hardware.device"})
                .get("config.hardware.device")).getVirtualDevice();

        Map<Integer, VirtualDevice> deviceMap = new HashMap<Integer, VirtualDevice>();
        for (VirtualDevice virtualDevice : listvd) {
            deviceMap.put(virtualDevice.getKey(), virtualDevice);
        }
        boolean found = false;
        for (VirtualDevice virtualDevice : listvd) {
            if (virtualDevice instanceof VirtualSCSIController) {
                VirtualSCSIController vscsic = (VirtualSCSIController)virtualDevice;
                int[] slots = new int[16];
                slots[7] = 1;
                List<Integer> devicelist = vscsic.getDevice();
                for (Integer deviceKey : devicelist) {
                    if (deviceMap.get(deviceKey).getUnitNumber() != null) {
                        slots[deviceMap.get(deviceKey).getUnitNumber()] = 1;
                    }
                }
                for (int i = 0; i < slots.length; i++) {
                    if (slots[i] != 1) {
                        retVal.add(vscsic.getKey());
                        retVal.add(i);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }

        if (!found) {
            throw new RuntimeException("The SCSI controller on the vm has maxed out its "
                + "capacity. Please add an additional SCSI controller");
        }
        return retVal;
    }

    private List<VirtualDeviceConfigSpec> createNicSpecs(List<VirtualDevice> devs) {
        List<VirtualDeviceConfigSpec> changeArr = new ArrayList<>();
        if (!devs.isEmpty()) {
            for (VirtualDevice dev : devs) {
                if (dev instanceof VirtualEthernetCard) {
                    VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
                    nicSpec.setOperation(VirtualDeviceConfigSpecOperation.REMOVE);
                    nicSpec.setDevice(dev);
                    changeArr.add(nicSpec);
                }
            }
        }
        for (String network : networks) {
            VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
            nicSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
            VirtualEthernetCard nic = new VirtualPCNet32();
            VirtualEthernetCardNetworkBackingInfo nicBacking = new VirtualEthernetCardNetworkBackingInfo();
            nicBacking.setDeviceName(netNameMap.get(network));
            nic.setAddressType("generated");
            nic.setBacking(nicBacking);
            nic.setKey(-1);
            nicSpec.setDevice(nic);
            changeArr.add(nicSpec);
        }
        return changeArr;
    }

    private List<VirtualDeviceConfigSpec> createDiskSpecs(List<VirtualDevice> devs)
        throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        List<VirtualDeviceConfigSpec> changeArr = new ArrayList<>();
        // 只保留第一块硬盘作为系统盘
        boolean findsys = false;
        for (VirtualDevice dev : devs) {
            if (dev instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk)dev;
                VirtualDeviceBackingInfo bak = disk.getBacking();
                if (bak instanceof VirtualDeviceFileBackingInfo) {
                    if (!findsys) {
                        findsys = true;
                    } else {
                        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
                        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.REMOVE);
                        diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.DESTROY);
                        diskSpec.setDevice(disk);
                        changeArr.add(diskSpec);
                    }
                }
            }
        }
        if (!findsys) {
            throw new IllegalArgumentException("Image [" + imagevm + "] has no system disk");
        }
        if (dataDiskSize > 0L) {
            changeArr.add(createDataDiskSpec(dataDiskSize));
        }

        return changeArr;
    }

    private VirtualMachineConfigSpec prepareVmConfig() throws Exception {

        VirtualMachineConfigSpec spec = new VirtualMachineConfigSpec();
        spec.setNumCPUs(2);
        spec.setMemoryMB(512L);

        VirtualMachineConfigInfo old =
            (VirtualMachineConfigInfo)getMOREFs.entityProps(imageVmref, new String[] {"config"}).get("config");
        List<VirtualDevice> devs = old.getHardware().getDevice();

        List<VirtualDeviceConfigSpec> changeArr = new ArrayList<>();
        changeArr.addAll(createNicSpecs(devs));
        changeArr.addAll(createDiskSpecs(devs));
        spec.getDeviceChange().addAll(changeArr);

        List<OptionValue> options = spec.getExtraConfig();
        OptionValue val = new OptionValue();
        val.setKey("RemoteDisplay.vnc.enabled");
        val.setValue("TRUE");
        options.add(val);
        val = new OptionValue();
        val.setKey("RemoteDisplay.vnc.port");
        val.setValue(vncport + "");
        options.add(val);
        return spec;
    }

    private VirtualDeviceConfigSpec createDataDiskSpec(long size) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();

        VirtualDisk disk = new VirtualDisk();
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
        int ckey = 0;
        int unitNumber = 0;
        List<Integer> getControllerKeyReturnArr = getControllerKey(imageVmref);
        if (!getControllerKeyReturnArr.isEmpty()) {
            ckey = getControllerKeyReturnArr.get(0);
            unitNumber = getControllerKeyReturnArr.get(1);
        }
        String fileName = "[" + selDatastoreName + "] volumes/" + dataDiskUUID + "/" + dataDiskUUID + ".vmdk";
        diskfileBacking.setFileName(fileName);
        diskfileBacking.setDiskMode("persistent");

        disk.setControllerKey(ckey);
        disk.setUnitNumber(unitNumber);
        disk.setBacking(diskfileBacking);
        disk.setCapacityInKB(size / 1024);
        disk.setKey(-1);

        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
        diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.CREATE);
        diskSpec.setDevice(disk);
        return diskSpec;
    }

    private VirtualMachineCloneSpec prepareCloneSpec() throws Exception {

        VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
        long minFreeSpace = findImageCapacity() + dataDiskSize;
        findHostAndDatastoreNameWithFreeSpace(minFreeSpace);
        if (hostmr == null) {
            throw new IllegalStateException("No host find");
        }

        final ManagedObjectReference crmor =
            (ManagedObjectReference)getMOREFs.entityProps(hostmr, new String[] {"parent"}).get("parent");

        final ManagedObjectReference resourcePoolRef =
            (ManagedObjectReference)getMOREFs.entityProps(crmor, new String[] {"resourcePool"}).get("resourcePool");

        VirtualMachineRelocateSpec relocSpec = new VirtualMachineRelocateSpec();
        cloneSpec.setConfig(prepareVmConfig());
        relocSpec.setPool(resourcePoolRef);
        relocSpec.setHost(hostmr);
        relocSpec.setDatastore(dsmor);
        cloneSpec.setLocation(relocSpec);
        cloneSpec.setPowerOn(startAfterCreate);
        cloneSpec.setTemplate(false);
        return cloneSpec;
    }

    private void cloneVM() throws Exception {
        final ManagedObjectReference vmFolderMor =
            (ManagedObjectReference)getMOREFs.entityProps(dcmor, new String[] {"vmFolder"}).get("vmFolder");
        VirtualMachineCloneSpec cloneSpec = prepareCloneSpec();
//        logger.info("Cloning Virtual Machine {} to clone name {}", imagevm, cloneName);
        ManagedObjectReference cloneTask = vimPort.cloneVMTask(imageVmref, vmFolderMor, cloneName, cloneSpec);
        if (!getTaskResultAfterDone(cloneTask)) {
            throw new IllegalStateException(
                "Failure Cloning VM Instance [" + imagevm + "] to clone name [" + cloneName + "]");
        }
        Map<String, ManagedObjectReference> refs =
            getMOREFs.inFolderByType(serviceContent.getRootFolder(), TYPE_VIRTUALMACHINE);
        clonedVmRef = refs.get(cloneName);
    }

    private void fillResult() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        createdInfo.setVm(clonedVmRef.getValue());
        List<CreatedDiskInfoDto> disks = new ArrayList<>();
        createdInfo.setDisks(disks);
        List<CreatedNetcardInfoDto> nics = new ArrayList<>();
        createdInfo.setNics(nics);

        List<VirtualDevice> listvd =
            ((ArrayOfVirtualDevice)getMOREFs.entityProps(clonedVmRef, new String[] {"config.hardware.device"})
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
