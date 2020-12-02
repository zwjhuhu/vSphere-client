package cn.com.skywin.cloudadmin.vsphere.task;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 从云盘的镜像复制一个文件创建一个vmdk文件，作为新的云盘，接口本身支持跨数据中心的复制 新建的云盘默认放在 目标存储的 volumes/diskUUID/diskUUID.vmdk
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class CreateDiskFromImageTask extends BaseTask<Object> {

    /**
     * 源datacenter的标识
     */
    private String sourceDatacenter;

    /**
     * 目的datacenter的标识
     */
    private String destDatacenter;

    /**
     * 源路径，需要去掉datasoure标记的部分，比如[datastore] images/xx/xx.vmdk
     */
    private String sourcePath;
    
    
    /**
     * 源存储的标识
     */
    private String srcDatastore;
    
    /**
     * 目标存储的标识
     */
    private String destDatastore;

    /**
     * 源存储的名称
     */
    private String srcDatastoreName;
    
    /**
     * 目标存储的名称
     */
    private String destDatastoreName;

    /**
     * 新云盘的uuid，这个就是数据库的主键
     */
    private String diskUUID;

    private ManagedObjectReference sdcmor = null;

    private ManagedObjectReference ddcmor = null;

    public CreateDiskFromImageTask(VsphereClient client, String sourceDatacenter, String destDatacenter,
        String sourcePath, String srcDatastore, String destDatastore, String diskUUID) {
        super(client);
        this.sourceDatacenter = sourceDatacenter;
        this.destDatacenter = destDatacenter;
        this.sourcePath = sourcePath;
        this.srcDatastore = srcDatastore;
        this.destDatastore = destDatastore;
        this.diskUUID = diskUUID;
    }

    @Override
    public Object doTask() throws Exception {
        copyFile();
        return null;
    }

    private void customValidation() throws Exception {
        if (sourcePath == null || sourcePath.length() < 1 || !sourcePath.matches("\\[.+\\] .+")) {
            throw new IllegalArgumentException("Specified path " + sourcePath + " not valid");
        }

        if (destDatastore == null || destDatastore.length() < 1) {
            throw new IllegalArgumentException("Specified Datastore [" + destDatastore + "] not valid");
        }

        if (sourceDatacenter != null && sourceDatacenter.length() != 0 && destDatacenter != null
            && destDatacenter.length() != 0) {
            sdcmor = findTypeReferenceById(TYPE_DATACENTER, sourceDatacenter);
            if (sdcmor == null) {
                throw new IllegalArgumentException("Specified Datacenter [" + sourceDatacenter + "] not Found");
            }
            Object[] ret = findTypeNameAndReferenceById(TYPE_DATASTORE, srcDatastore);
            ManagedObjectReference dsmor = null;
            if(ret!=null) {
                srcDatastoreName = (String)ret[0];
                dsmor = (ManagedObjectReference)ret[1];
            }
            if (dsmor == null) {
                throw new IllegalArgumentException("Specified Datastore [" + sourceDatacenter
                    + "] was not found in specified Datacenter [" + srcDatastore + "]");
            }
            
            if (sourceDatacenter.equals(destDatacenter)) {
                ddcmor = sdcmor;
            } else {
                ddcmor = findTypeReferenceById(TYPE_DATACENTER, destDatacenter);
                if (ddcmor == null) {
                    throw new IllegalArgumentException("Specified Datacenter [" + destDatacenter + "] not Found");
                }
            }
            if(srcDatastore.equals(destDatastore)) {
                destDatastoreName = srcDatastoreName;
            }else {
                ret = findTypeNameAndReferenceById(TYPE_DATASTORE, destDatastore);
                dsmor = null;
                if(ret!=null) {
                    destDatastoreName = (String)ret[0];
                    dsmor = (ManagedObjectReference)ret[1];
                }
                if (dsmor == null) {
                    throw new IllegalArgumentException("Specified Datastore [" + destDatacenter
                        + "] was not found in specified Datacenter [" + destDatacenter + "]");
                }
            }

        } else {
            throw new IllegalArgumentException("Source Datacenter or Dest Datacenter not specified");
        }

    }

    private void copyFile() throws Exception {
        customValidation();
        ManagedObjectReference fileManager = serviceContent.getFileManager();
        String destDir = "[" + destDatastoreName + "] volumes/"+diskUUID;
        String destPath = destDir + "/" + diskUUID+".vmdk";
        int pos = sourcePath.indexOf("] ");
        if(pos>0) {
            sourcePath = sourcePath.substring(pos+2);
        }
        sourcePath = "["+srcDatastoreName +"] " + sourcePath;
        boolean createParentDirectories = true;
        vimPort.makeDirectory(fileManager, destDir, ddcmor, createParentDirectories);
        boolean force = false;
//        logger.info("copy file {} to {}",sourcePath,destPath);
        ManagedObjectReference reftask =
            vimPort.copyDatastoreFileTask(fileManager, sourcePath, sdcmor, destPath, ddcmor, force);
        if (!getTaskResultAfterDone(reftask)) {
            throw new IllegalStateException("Some Exception While copy file from " + sourcePath + " to " + destPath);
        }
    }
}
