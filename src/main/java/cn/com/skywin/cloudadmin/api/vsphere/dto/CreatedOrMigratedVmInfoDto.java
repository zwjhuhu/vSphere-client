package cn.com.skywin.cloudadmin.api.vsphere.dto;

import java.util.List;

/**
 * 创建或迁移云主机后，需要返回一些信息，主要是虚机的标识，云盘的路径和datastore等
 * @author zwj
 * @date 2020/11/30
 */
public class CreatedOrMigratedVmInfoDto {
    
    public static class CreatedDiskInfoDto{
        /**
         * 挂载到云主机之后有一个key，卸载时需要用到
         */
        private int key;
        
        /**
         * 容量
         */
        private long capacity;
        
        /**
         * 数据存储标记
         */
        private String datastore;
        
        /**
         * 文件路径
         */
        private String path;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public long getCapacity() {
            return capacity;
        }

        public void setCapacity(long capacity) {
            this.capacity = capacity;
        }

        public String getDatastore() {
            return datastore;
        }

        public void setDatastore(String datastore) {
            this.datastore = datastore;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
    
    public static class CreatedNetcardInfoDto{
        /**
         * 挂载到云主机之后有一个key，卸载时需要用到
         */
        private String label;
        
        /**
         * mac 地址
         */
        private String mac;
        
        /**
         * 关联的网络标识
         */
        private String network;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }
        
    } 
    
    /**
     * 虚机的标识
     */
    private String vm;
    
    /**
     * 硬盘信息
     */
    private List<CreatedDiskInfoDto> disks;
    
    /**
     * 网卡信息
     */
    private List<CreatedNetcardInfoDto> nics;

    public String getVm() {
        return vm;
    }


    public void setVm(String vm) {
        this.vm = vm;
    }


    public List<CreatedDiskInfoDto> getDisks() {
        return disks;
    }


    public void setDisks(List<CreatedDiskInfoDto> disks) {
        this.disks = disks;
    }


    public List<CreatedNetcardInfoDto> getNics() {
        return nics;
    }


    public void setNics(List<CreatedNetcardInfoDto> nics) {
        this.nics = nics;
    }
    
}
