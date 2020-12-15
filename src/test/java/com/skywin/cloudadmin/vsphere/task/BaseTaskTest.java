 package com.skywin.cloudadmin.vsphere.task;

import com.github.kubesys.vsphere.VsphereClient;

/**
 * @author zwj
 * @date 2020/12/01
 */
public abstract class BaseTaskTest {
    
    protected VsphereClient client;
    
    public BaseTaskTest() {
        try {
            client = new VsphereClient("https", "192.168.23.148", 443, 
                "administrator@vsphere.local", "Qwe@1234", "6.5");
        }catch (Exception e) {
        	e.printStackTrace();
            client = null;
        }
    }
    

}
