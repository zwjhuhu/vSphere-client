package cn.com.skywin.cloudadmin.vsphere.task;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 从数据存储删除文件或者文件夹，用在删除云盘或者镜像文件
 * @author zwj
 * @date 2020/11/28
 */
public class DeleteFileTask extends BaseTask<Object> {

    /**
     * datacenter的标识
     */
    private String datacenter;



    /**
     * 文件或文件夹的路径，这里指的是包含datastore的路径 比如[datastore] images/xxx/xxx.vmdk
     */
    private String path;
    
    /**
     * datastore的标识
     */
    private String datastore;
    
    private String datastoreName;

    private ManagedObjectReference dcmor = null;

    public DeleteFileTask(VsphereClient client, String datacenter, String datastore,String path) {
        super(client);
        this.datacenter = datacenter;
        this.datastore = datastore;
        this.path = path;
    }

    @Override
    public Object doTask() throws Exception {
        destroyFileOrDir();
        return null;
    }

    private void customValidation() throws Exception {
        if (path == null || path.length() < 1 ||!path.matches("\\[.+\\] .+") ) {
            throw new IllegalArgumentException("Specified path [" + path + "] not valid");
        }
        if (datacenter != null && datacenter.length() != 0 && datastore != null && datastore.length() != 0) {
            dcmor = findTypeReferenceById(TYPE_DATACENTER, datacenter);
            if (dcmor != null) {
                Object[] ret = findTypeNameAndReferenceById(TYPE_DATASTORE, datastore);
                ManagedObjectReference dsmor = null;
                if(ret!=null) {
                    datastoreName = (String)ret[0];
                    dsmor = (ManagedObjectReference)ret[1];
                }
                if (dsmor == null) {
                    throw new IllegalArgumentException(
                        "Specified Datastore [" + datastore + "] was not" + " found in specified Datacenter ["+datacenter+"]");
                }
            } else {
                throw new IllegalArgumentException("Specified Datacenter [" + datacenter + "] not Found");
            }
        } else {
            throw new IllegalArgumentException("Datacenter or Datastore not specified");
        }

    }

    private void destroyFileOrDir() throws Exception {
        customValidation();
        ManagedObjectReference fileManager = serviceContent.getFileManager();
        
        int pos = path.indexOf("] ");
        if(pos>0) {
            path = path.substring(pos+2);
        }
        path = "["+datastoreName +"] " + path;
        ManagedObjectReference reftask = vimPort.deleteDatastoreFileTask(fileManager, path, dcmor);
        try {
            if (!getTaskResultAfterDone(reftask)) {
                throw new IllegalStateException("Some Exception While Delete file [" + path + "]");
            }
        }catch (Exception e) {
            String msg = e.getMessage();
            if(msg!=null&&msg.contains(path + " was not found")) {
//                logger.info(e.getMessage());
            }else {
                throw e;
            }
        }
    }
}
