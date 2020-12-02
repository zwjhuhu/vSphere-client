package cn.com.skywin.cloudadmin.api.vsphere.info.template;

/**
 * 上传镜像后，镜像模板的信息
 * @author zwj
 * @date 2020/11/28
 */
public class UploadTemplateDto {
	
	/**
	 * 虚拟的容量（vmware的置备容量）,单位Byte
	 */
	private Long capacity;
	
	/**
	 * 实际使用的真实容量,单位Byte
	 */
	private Long realcapacity;
	
	/**
	 * 文件路径
	 */
	private String vmdkfile;
	
	/**
	 * 镜像服务器的标识
	 */
	private String datastore;
	
	/**
	 * 系统类型
	 */
	private String guestos;
	
	
	/**
	 * 这个是vcenter中的虚机标记，例如"vm-21"
	 */
	private String vm;


    public Long getCapacity() {
        return capacity;
    }


    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }


    public Long getRealcapacity() {
        return realcapacity;
    }


    public void setRealcapacity(Long realcapacity) {
        this.realcapacity = realcapacity;
    }


    public String getVmdkfile() {
        return vmdkfile;
    }


    public void setVmdkfile(String vmdkfile) {
        this.vmdkfile = vmdkfile;
    }


    public String getDatastore() {
        return datastore;
    }


    public void setDatastore(String datastore) {
        this.datastore = datastore;
    }


    public String getGuestos() {
        return guestos;
    }


    public void setGuestos(String guestos) {
        this.guestos = guestos;
    }


    public String getVm() {
        return vm;
    }


    public void setVm(String vm) {
        this.vm = vm;
    }

}
