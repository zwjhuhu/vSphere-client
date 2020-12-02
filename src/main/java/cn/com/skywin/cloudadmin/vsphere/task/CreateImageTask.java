package cn.com.skywin.cloudadmin.vsphere.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FilenameUtils;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.connection.KeepAlive;
import com.vmware.vim25.ArrayOfDatastoreHostMount;
import com.vmware.vim25.ConfigTarget;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostMountInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualLsiLogicController;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineDatastoreInfo;
import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.VirtualMachineGuestOsIdentifier;
import com.vmware.vim25.VirtualMachineStorageSummary;
import com.vmware.vim25.VirtualSCSISharing;

import cn.com.skywin.cloudadmin.api.vsphere.info.template.UploadTemplateDto;

/**
 * 创建vcenter中的镜像<br/>
 * <p>
 * 步骤：<br/>
 * 1. 上传vmdk文件到vcenter的数据存储<br/>
 * 2. 创建一个虚机使用vmdk文件<br/>
 * 3. 将虚机设置为模板<br/>
 * </p>
 * <p>
 * <b>经测试，如果这个任务本身创建虚机步骤失败，是自动清理资源的，不用手动删除上传的文件<br/>
 * 如果是保存数据出错，需要回滚删除则先执行{@link DestroyVMOrImageTask} <br/>
 * 之后执行 {@link DeleteFileTask}
 * </b>
 * </p>
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class CreateImageTask extends BaseTask<UploadTemplateDto> {

    /**
     * 镜像的数据库据id
     */
    private String mirrorId;

    /**
     * 镜像的名称，这个名称是注册到vcenter中的名称
     */
    private String name = "";

    /**
     * 镜像的平台Linux/Windows/Other
     */
    private String platform;

    /**
     * 数据中心标识
     */
    private String datacenter;

    /**
     * 数据存储标识
     */
    private String datastore;

    /**
     * 需要上传的文件路径，一般使用vmdk结尾的文件
     */
    private String localPath;
    
    private String datacenterName;
    
    private String datastoreName;

    private ManagedObjectReference dcmor = null;

    private ManagedObjectReference dsmor = null;

    private UploadTemplateDto dto = new UploadTemplateDto();

    public CreateImageTask(VsphereClient client, String mirrorId, String name, String platform, String datacenter,
        String datastore, String localPath) {
        super(client);
        this.mirrorId = mirrorId;
        this.name = name;
        this.platform = platform;
        this.datacenter = datacenter;
        this.datastore = datastore;
        this.localPath = localPath;
    }

    @Override
    public UploadTemplateDto doTask() throws Exception {
        putVMDKFileAndCreateVmTemp();
        return dto;
    }

    private void customValidation() throws Exception {

        if (localPath == null || !new File(localPath).exists()) {
            throw new IllegalArgumentException("Specified localPath [" + localPath + "] not exists");
        }

        if (datacenter != null && datacenter.length() != 0 && datastore != null && datastore.length() != 0) {
            Object[] ret = findTypeNameAndReferenceById(TYPE_DATACENTER, datacenter);
            if(ret!=null) {
                datacenterName = (String)ret[0];
                dcmor = (ManagedObjectReference)ret[1];
            }
            if (dcmor != null) {
                ret = findTypeNameAndReferenceById(TYPE_DATASTORE, datastore);
                if(ret!=null) {
                    datastoreName = (String)ret[0];
                    dsmor = (ManagedObjectReference)ret[1];
                }
                if (dsmor == null) {
                    throw new IllegalArgumentException("Specified Datastore [" + datastore
                        + "] was not found in specified Datacenter [" + datacenter + "]");
                }
            } else {
                throw new IllegalArgumentException("Specified Datacenter [" + datacenter + "] not Found");
            }
        } else {
            throw new IllegalArgumentException("Datacenter or Datastore not specified");
        }

    }

    @SuppressWarnings("unchecked")
    private void putVMFile(final String remoteFilePath, final File localFile) throws Exception {
        final String url = connection.getUrl();
        final String serviceUrl = url.substring(0, url.lastIndexOf("sdk") - 1);
        String httpUrl = serviceUrl + "/folder/" + remoteFilePath + "?dcPath=" + datacenterName + "&dsName=" + datastoreName;
        httpUrl = httpUrl.replaceAll("\\ ", "%20");
//        logger.info("Putting VM File to url {}", httpUrl);

        final URL fileURL;
        HttpURLConnection conn = null;
        try {
            fileURL = new URL(httpUrl);
            conn = (HttpURLConnection)fileURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(true);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Maintain session
        final List<String> cookies = (List<String>)headers.get("Set-cookie");
        String cookieValue = (cookies != null) ? cookies.get(0) : "";
        final StringTokenizer tokenizer = new StringTokenizer(cookieValue, ";");
        cookieValue = tokenizer.nextToken();
        final String path = "$" + tokenizer.nextToken();
        final String cookie = "$Version=\"1\"; " + cookieValue + "; " + path;

        // set the cookie in the new request header
        final Map<String, List<String>> map = new HashMap<>();
        map.put("Cookie", Collections.singletonList(cookie));
        ((BindingProvider)vimPort).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, map);

        conn.setRequestProperty("Cookie", cookie);
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Length", "1024");
        long fileLen = localFile.length();
//        logger.info("File size is: {}", fileLen);

        // setChunkedStreamingMode to -1 turns off chunked mode
        // setChunkedStreamingMode to 0 asks for system default
        // NOTE:
        // larger values mean faster connections at the
        // expense of more heap consumption.
        conn.setChunkedStreamingMode(0);

        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = new BufferedInputStream(new FileInputStream(localFile));
            int bufLen = 9 * 1024;
            byte[] buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            // this can take a very long time, so we do a keep-alive here.
            Thread keepAlive = KeepAlive.keepAlive(vimPort, getServiceInstanceReference());
            keepAlive.start();
            // final String[] spinner = new String[] {"\u0008/", "\u0008-", "\u0008\\", "\u0008|"};
            // logger.info(".");
            // int i = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                out.write(tmp, 0, len);
                // logger.info("%s", spinner[i++ % spinner.length]);
            }
            // logger.info("\u0008");
            keepAlive.interrupt();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                // ignore
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                // ignore
            }
            try {
                conn.getResponseCode();
            } catch (Exception e) {
                throw e;
            } finally {
                conn.disconnect();
            }
        }

    }

    private String getGuestId() {
        if ("linux".equalsIgnoreCase(platform)) {
            return VirtualMachineGuestOsIdentifier.CENTOS_7_64_GUEST.value();
        } else if ("windows".equals(platform)) {
            return VirtualMachineGuestOsIdentifier.WINDOWS_9_SERVER_64_GUEST.value();
        }
        return VirtualMachineGuestOsIdentifier.OTHER_GUEST_64.value();
    }

    private void createVirtualMachine(String remotePath) throws Exception {
//        logger.info("Create The Virtual Machine ...");
        ManagedObjectReference hostmor = null;

        final List<DatastoreHostMount> hostmounts =
            ((ArrayOfDatastoreHostMount)getMOREFs.entityProps(dsmor, new String[] {"host"}).get("host"))
                .getDatastoreHostMount();

        for (DatastoreHostMount datastoreHostMount : hostmounts) {
            if (datastoreHostMount == null) {
                throw new IllegalStateException("datastore [" + datastoreName + "] has no host mounts!");
            }
            HostMountInfo mountInfo = datastoreHostMount.getMountInfo();
            if (mountInfo == null) {
                throw new IllegalStateException("datastoreHostMount on [" + datastoreName + "] has no info!");
            }

            final Boolean accessible = mountInfo.isAccessible();
            // the values "accessible" and "mounted" need not be set by the server.
            final Boolean mounted = mountInfo.isMounted();
            // if mounted is not set, assume it is true
            if ((accessible != null && accessible) && (mounted == null || mounted)) {
                hostmor = datastoreHostMount.getKey();
                break;
            }

        }
        if (hostmor == null) {
            throw new IllegalStateException("No host connected to the datastore [" + datastoreName + "]");
        }

        final ManagedObjectReference crmor =
            (ManagedObjectReference)getMOREFs.entityProps(hostmor, new String[] {"parent"}).get("parent");

        final ManagedObjectReference resourcePoolRef =
            (ManagedObjectReference)getMOREFs.entityProps(crmor, new String[] {"resourcePool"}).get("resourcePool");

        final ManagedObjectReference vmFolderMor =
            (ManagedObjectReference)getMOREFs.entityProps(dcmor, new String[] {"vmFolder"}).get("vmFolder");

//        logger.info("trying to create vm template {}", name);
        VirtualMachineConfigSpec vmConfigSpec = createVmConfigSpec(remotePath, datastoreName, crmor, hostmor);
        vmConfigSpec.setName(name);
        // vmConfigSpec.setAnnotation("VirtualMachine Annotation");
        vmConfigSpec.setMemoryMB(1024L);
        vmConfigSpec.setNumCPUs(1);
        vmConfigSpec.setGuestId(getGuestId());
        // Creating The Virtual machine
        final ManagedObjectReference taskmor =
            vimPort.createVMTask(vmFolderMor, vmConfigSpec, resourcePoolRef, hostmor);

        if (getTaskResultAfterDone(taskmor)) {
            ManagedObjectReference registeredVMRef =
                (ManagedObjectReference)getMOREFs.entityProps(taskmor, new String[] {"info.result"}).get("info.result");
//            logger.info("VM registered with value {} ", registeredVMRef.getValue());
            vimPort.markAsTemplate(registeredVMRef);
            dto.setVm(registeredVMRef.getValue());

            VirtualMachineStorageSummary summary = (VirtualMachineStorageSummary)getMOREFs
                .entityProps(registeredVMRef, new String[] {"summary.storage"}).get("summary.storage");

            dto.setCapacity(summary.getUncommitted());
            dto.setRealcapacity(summary.getCommitted());

        } else {
            throw new IllegalStateException("Some Exception While Create The VM [" + name + "]");
        }

    }

    private ConfigTarget getConfigTargetForHost(ManagedObjectReference computeResMor, ManagedObjectReference hostMor)
        throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference envBrowseMor = (ManagedObjectReference)getMOREFs
            .entityProps(computeResMor, new String[] {"environmentBrowser"}).get("environmentBrowser");
        ConfigTarget configTarget = vimPort.queryConfigTarget(envBrowseMor, hostMor);
        if (configTarget == null) {
            throw new RuntimeException("No ConfigTarget found in ComputeResource");
        }
        return configTarget;
    }

    private String getVolumeName(String volName) {
        String volumeName = null;
        if (volName != null && volName.length() > 0) {
            volumeName = "[" + volName + "]";
        } else {
            volumeName = "[Local]";
        }

        return volumeName;
    }

    private VirtualDeviceConfigSpec createVirtualDisk(String datastoreName, int diskCtlrKey, String vmdkpath) {
        String volumeName = getVolumeName(datastoreName);
        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();

        // 使用现有硬盘，必须不指定VirtualDeviceConfigSpecFileOperation
        // diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.REPLACE);
        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);

        VirtualDisk disk = new VirtualDisk();
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();

        diskfileBacking.setFileName(volumeName + " " + vmdkpath);
        diskfileBacking.setDiskMode("persistent");
        // diskfileBacking.setDatastore(datastoreRef);

        disk.setKey(0);
        disk.setControllerKey(diskCtlrKey);
        disk.setUnitNumber(0);
        disk.setBacking(diskfileBacking);
        // disk.setCapacityInKB(4*1024*1024);
        diskSpec.setDevice(disk);

        return diskSpec;
    }

    private VirtualMachineConfigSpec createVmConfigSpec(String remotePath, String datastoreName,
        ManagedObjectReference computeResMor, ManagedObjectReference hostMor) throws Exception {

        ConfigTarget configTarget = getConfigTargetForHost(computeResMor, hostMor);
        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();

        if (datastoreName != null) {
            boolean flag = false;
            for (int i = 0; i < configTarget.getDatastore().size(); i++) {
                VirtualMachineDatastoreInfo vdsInfo = configTarget.getDatastore().get(i);
                DatastoreSummary dsSummary = vdsInfo.getDatastore();
                if (dsSummary.getName().equals(datastoreName)) {
                    flag = true;
                    if (!dsSummary.isAccessible()) {
                        throw new IllegalStateException("Specified Datastore[" + datastoreName + "] is not accessible");
                    }
                    break;
                }
            }
            if (!flag) {
                throw new IllegalStateException("Specified Datastore [" + datastoreName + "]is not Found");
            }
        } else {
            boolean flag = false;
            for (int i = 0; i < configTarget.getDatastore().size(); i++) {
                VirtualMachineDatastoreInfo vdsInfo = configTarget.getDatastore().get(i);
                DatastoreSummary dsSummary = vdsInfo.getDatastore();
                if (dsSummary.isAccessible()) {
                    datastoreName = dsSummary.getName();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new IllegalStateException("No Datastore found on host");
            }
        }
        String datastoreVolume = getVolumeName(datastoreName);
        VirtualMachineFileInfo vmfi = new VirtualMachineFileInfo();
        vmfi.setVmPathName(datastoreVolume + " images/" + mirrorId + "/" + name + ".vmx");
        configSpec.setFiles(vmfi);
        // Add a scsi controller
        int diskCtlrKey = 1;
        VirtualDeviceConfigSpec scsiCtrlSpec = new VirtualDeviceConfigSpec();
        scsiCtrlSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
        VirtualLsiLogicController scsiCtrl = new VirtualLsiLogicController();
        scsiCtrl.setBusNumber(0);
        scsiCtrlSpec.setDevice(scsiCtrl);
        scsiCtrl.setKey(diskCtlrKey);
        scsiCtrl.setSharedBus(VirtualSCSISharing.NO_SHARING);
        // String ctlrType = scsiCtrl.getClass().getName();
        // ctlrType = ctlrType.substring(ctlrType.lastIndexOf(".") + 1);

        // Create a new disk - file based - for the vm
        VirtualDeviceConfigSpec diskSpec = null;
        String vmdkpath = remotePath;
        while (remotePath.startsWith("/")) {
            vmdkpath = remotePath.substring(1);
        }
        diskSpec = createVirtualDisk(datastoreName, diskCtlrKey, vmdkpath);
        List<VirtualDeviceConfigSpec> deviceConfigSpec = new ArrayList<>();
        deviceConfigSpec.add(scsiCtrlSpec);
        deviceConfigSpec.add(diskSpec);
        configSpec.getDeviceChange().addAll(deviceConfigSpec);
        return configSpec;
    }

    private void putVMDKFileAndCreateVmTemp() throws Exception {
        customValidation();
        if (getMOREFs.inContainerByType(serviceContent.getRootFolder(), "VirtualMachine").containsKey(name)) {
            throw new IllegalArgumentException(String.format("VM Instance [%s] already exists!", name));
        }

        String basename = FilenameUtils.getBaseName(localPath);
        String remotePath = "images/" + mirrorId + "/" + basename + ".vmdk";
        dto.setDatastore(datastore);
        dto.setVmdkfile(getVolumeName(datastoreName) + " " + remotePath);
        dto.setGuestos(getGuestId());
        putVMFile(remotePath, new File(localPath));
        createVirtualMachine(remotePath);
    }
}
