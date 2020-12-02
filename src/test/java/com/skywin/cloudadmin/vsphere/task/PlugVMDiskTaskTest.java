package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.com.skywin.cloudadmin.commons.UUIDGenerator;
import cn.com.skywin.cloudadmin.vsphere.task.PlugVMDiskTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class PlugVMDiskTaskTest  extends BaseTaskTest {

    @Test
    public void testPlugVMDiskTaskNew1() throws Exception {
        PlugVMDiskTask task = new PlugVMDiskTask(
            client,
            "vm-402",false,1L*1024*1024*1024,null,null,UUIDGenerator.gen());
        System.out.println(JSON.toJSON(task.process()));
    }
    
    @Test
    public void testPlugVMDiskTaskNew2() throws Exception {
        PlugVMDiskTask task = new PlugVMDiskTask(
            client,
            "vm-402",false,1L*1024*1024*1024,null,"datastore-18",UUIDGenerator.gen());
        System.out.println(JSON.toJSON(task.process()));
    }
    
    @Test
    public void testPlugVMDiskTaskFromImage() throws Exception {
        String path = 
            "[datastore1] volumes/18e1d85597974178b37c1707a31b0bed/18e1d85597974178b37c1707a31b0bed.vmdk";
        PlugVMDiskTask task = new PlugVMDiskTask(
            client,
            "vm-402",true,0L,path,"datastore-18",UUIDGenerator.gen());
        System.out.println(JSON.toJSON(task.process()));
    }

}
