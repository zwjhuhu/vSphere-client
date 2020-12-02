 package cn.com.skywin.cloudadmin.api.vsphere.dto;

/**
  * 
 * @author zwj
 * @date 2020/04/02
 */
public class VmDiskInfoDto {
    
    public static final String TYPE_SCSI = "SCSI";
    
    /**
     * 标识
     */
    private String disk;
    
    /**
     * 后端存储信息
     */
    private Backing backing;
    
    /**
     * 容量 Byte
     */
    private Long capacity;
    
    /**
     * 标签
     */
    private String label;
    
    
    /**
     * datastore标识
     */
    private String datastore;
    
    
    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public Backing getBacking() {
        return backing;
    }

    public void setBacking(Backing backing) {
        this.backing = backing;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDatastore() {
        return datastore;
    }

    public void setDatastore(String datastore) {
        this.datastore = datastore;
    }

    public static class Backing{
        private String type;
        
        private String vmdk_file;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

		public String getVmdk_file() {
			return vmdk_file;
		}

		public void setVmdk_file(String vmdk_file) {
			this.vmdk_file = vmdk_file;
		}
    }
    
}
