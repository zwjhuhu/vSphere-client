package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.ArrayOfVirtualDevice;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceFileBackingInfo;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualSCSIController;

import cn.com.skywin.cloudadmin.api.vsphere.dto.VmDiskInfoDto;

/**
 * 为云主机加载硬盘有两种情况，一种是从云盘规格创建的这个只需要大小，另一种是从云盘镜像创建这个需要文件的路径<br>
 * 挂载成功需要返回一个key值，卸载的时候需要使用
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class PlugVMDiskTask extends BaseTask<VmDiskInfoDto> {

    /**
     * 虚机的标识
     */
    private String vm;

    /**
     * 是否从云盘镜像创建，为true时不需要指定disksize
     */
    private boolean fromImage;

    /**
     * 云盘容量大小，单位Byte
     */
    private long disksize;

    /**
     * 从云盘创建时，需要指定已经创建出来的云盘的位置 格式 [datastore] volumes/xx/xx.vmdk
     */
    private String diskpath;

    /**
     * 创建云盘的存储,如果是从镜像创建必填，如果从云盘规格创建可以为空
     */
    private String datastore;

    /**
     * 云盘的UUID，指数据库的主键，这个只有从云盘规格创建的时候需要指定
     */
    private String diskUUID;

    private ManagedObjectReference virtualMachine = null;
    
    private String datastoreName;
    
    private int ckey = 0;
    private int unitNumber = 0;

    public PlugVMDiskTask(VsphereClient client, String vm, boolean fromImage,
        long disksize, String diskpath, String datastore,String diskUUID) {
        super(client);
        this.vm = vm;
        this.fromImage = fromImage;
        this.disksize = disksize;
        this.diskpath = diskpath;
        this.datastore = datastore;
        this.diskUUID = diskUUID;
    }

    @Override
    public VmDiskInfoDto doTask() throws Exception {
        customValidation();
        reConfig();
        return findDiskKey();
    }
    
    private VmDiskInfoDto findDiskKey() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        VmDiskInfoDto dto = null;
        List<VirtualDevice> listvd =
            ((ArrayOfVirtualDevice)getMOREFs.entityProps(virtualMachine, new String[] {"config.hardware.device"})
                .get("config.hardware.device")).getVirtualDevice();
        for(VirtualDevice dev:listvd) {
            if(dev instanceof VirtualDisk) {
                VirtualDisk disk  = (VirtualDisk) dev;
                if(disk.getControllerKey() == ckey && disk.getUnitNumber() == unitNumber) {
                    if(disk.getBacking() instanceof VirtualDeviceFileBackingInfo) {
                        dto = new VmDiskInfoDto();
                        VirtualDeviceFileBackingInfo bak = (VirtualDeviceFileBackingInfo)disk.getBacking();
                        VmDiskInfoDto.Backing baking = new VmDiskInfoDto.Backing();
                        baking.setVmdk_file(bak.getFileName());
                        dto.setBacking(baking);
                        dto.setCapacity(disk.getCapacityInKB() * 1024);
                        dto.setDisk(disk.getKey()+"");
                        dto.setLabel(disk.getDeviceInfo().getLabel());
                        dto.setDatastore(bak.getDatastore().getValue());
                    }
                }
            }
        }
        return dto;
    }

    private void customValidation() throws Exception {
        virtualMachine = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (virtualMachine == null) {
            throw new IllegalArgumentException("VM Instances [" + vm + "] Not Found");
        }
        
        boolean autoSel = false;
        if(datastore!=null) {
            Object[] ret = findTypeNameAndReferenceById(TYPE_DATASTORE, datastore);
            if(ret!=null) {
                datastoreName = (String)ret[0];
            }
        }else {
            autoSel = true;
        }
        

        if(!fromImage) {
            if(diskUUID==null) {
                throw new IllegalArgumentException("VM Disk UUID should be specified");
            }
            if(disksize<1024L*1024) {
                // 不能小于1MB
                throw new IllegalArgumentException("VM Disk size should be larger than 1MB");
            }
            if(!autoSel && datastoreName==null) {
                throw new IllegalArgumentException("Specified Datastore [" + datastore
                    + "] was not found");
            }
            datastoreName = getDatastoreNameWithFreeSpace(disksize);
            if(datastoreName==null) {
                throw new IllegalArgumentException("No Datastore was not found or "
                    + "free capacity less than ["+disksize+"] bytes");
            }
        }else {
            if(datastoreName==null) {
                throw new IllegalArgumentException("Need specify datasotre");
            }
            if (diskpath == null || diskpath.length() < 1 || !diskpath.matches("\\[.+\\] .+")) {
                throw new IllegalArgumentException("Specified path " + diskpath + " not valid");
            }
        }

    }

    /**
     * 从需要的容量选择数据存储
     * 
     * @param minFreeSpace
     * @return
     * @throws Exception
     */
    private String getDatastoreNameWithFreeSpace(long minFreeSpace) throws Exception {
        String dsName = null;        
        // 需要取出物理机上可以访问到的datastore
        ManagedObjectReference br = (ManagedObjectReference)getMOREFs.entityProps(virtualMachine, new String[] {"environmentBrowser"})
            .get("environmentBrowser");
        br = (ManagedObjectReference)getMOREFs.entityProps(br, new String[] {"datastoreBrowser"})
            .get("datastoreBrowser");
        List<ManagedObjectReference> datastores = ((ArrayOfManagedObjectReference)getMOREFs
            .entityProps(br, new String[] {"datastore"}).get("datastore")).getManagedObjectReference();
        for (ManagedObjectReference ref : datastores) {
            DatastoreSummary ds =
                (DatastoreSummary)getMOREFs.entityProps(ref, new String[] {"summary"}).get("summary");
            if (StringUtils.isNotBlank(datastoreName)) {
                if(datastoreName.equals(ds.getName())&&ds.getFreeSpace() > minFreeSpace) {
                    dsName = ds.getName();
                }
            }else {
                if (ds.getFreeSpace() > minFreeSpace) {
                    dsName = ds.getName();
                }
            }
            if(dsName!=null) {
                break;
            }
        }
        return dsName;
    }

    private List<Integer> getControllerKey(ManagedObjectReference vmMor) throws Exception {
        List<Integer> retVal = new ArrayList<>();

        List<VirtualDevice> listvd =
            ((ArrayOfVirtualDevice)getMOREFs.entityProps(vmMor, new String[] {"config.hardware.device"})
                .get("config.hardware.device")).getVirtualDevice();

        Map<Integer, VirtualDevice> deviceMap = new HashMap<>();
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
            throw new IllegalStateException("The SCSI controller on the VM [" + vm + "] no exists or has maxed out its "
                + "capacity. Please add an additional SCSI controller");
        }
        return retVal;
    }

    private VirtualDeviceConfigSpec getDiskDeviceConfigSpec() throws Exception {
        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();

        VirtualDisk disk = new VirtualDisk();
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();

        List<Integer> getControllerKeyReturnArr = getControllerKey(virtualMachine);
        if (!getControllerKeyReturnArr.isEmpty()) {
            ckey = getControllerKeyReturnArr.get(0);
            unitNumber = getControllerKeyReturnArr.get(1);
        }
        if(!fromImage) {
            diskpath = "[" + datastoreName + "] volumes/" + diskUUID + "/" + diskUUID+".vmdk";
        }else {
            int pos = diskpath.indexOf("] ");
            if(pos>0) {
                diskpath = diskpath.substring(pos+2);
            }
            diskpath = "["+datastoreName +"] " + diskpath;
        }
        diskfileBacking.setFileName(diskpath);
        diskfileBacking.setDiskMode("persistent");

        disk.setControllerKey(ckey);
        disk.setUnitNumber(unitNumber);
        disk.setBacking(diskfileBacking);
        if(!fromImage) {
            disk.setCapacityInKB(disksize/1024);
        }
        disk.setKey(-1);
        if(!fromImage) {
            diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.CREATE);
        }
        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
        diskSpec.setDevice(disk);

        return diskSpec;
    }

    private void reConfig() throws Exception {
        VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

//        logger.info("Reconfiguring The Virtual Machine For Disk Update {}",vm);
        VirtualDeviceConfigSpec vdiskSpec = getDiskDeviceConfigSpec();
        List<VirtualDeviceConfigSpec> vdiskSpecArray = new ArrayList<>();
        vdiskSpecArray.add(vdiskSpec);
        vmConfigSpec.getDeviceChange().addAll(vdiskSpecArray);

        ManagedObjectReference tmor = vimPort.reconfigVMTask(virtualMachine,vmConfigSpec);
        if(!getTaskResultAfterDone(tmor)) {
            throw new IllegalStateException("VM Instance [" + vm + "] reconfigure failed");
        }
    }
    
}
