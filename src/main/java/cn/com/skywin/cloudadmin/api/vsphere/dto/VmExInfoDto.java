package cn.com.skywin.cloudadmin.api.vsphere.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补充软件所接口不能获取的信息
 * 如果虚机安装了vmware tools 应该可以获取到内部ip和网卡的关联信息
 * @author zwj
 * @date 2020/11/24
 */
public class VmExInfoDto {
    
    
    /**
     * vnc访问密码，如果没有配置返回""
     * 
     */
    private String vncpassword="";
    
    /**
     * vnc端口，如果没有配置返回0
     */
    private int vncport=0;
    
    
    /**
     * 默认ip
     */
    private String defIp;
    
    /**
     * 默认ip的mac
     */
    private String defMac;

    /**
     * 网卡mac地址和ip的对应关系
     */
    private final Map<String,List<String>> ips = new HashMap<>();

    public String getDefIp() {
        return defIp;
    }

    public void setDefIp(String defIp) {
        this.defIp = defIp;
    }

    public Map<String, List<String>> getIps() {
        return ips;
    }

    public String getDefMac() {
        return defMac;
    }

    public void setDefMac(String defMac) {
        this.defMac = defMac;
    }

    public String getVncpassword() {
        return vncpassword;
    }

    public void setVncpassword(String vncpassword) {
        this.vncpassword = vncpassword;
    }

    public int getVncport() {
        return vncport;
    }

    public void setVncport(int vncport) {
        this.vncport = vncport;
    }
    
}
