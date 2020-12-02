package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.DestroyVMOrImageTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class DestroyVMOrImageTaskTest  extends BaseTaskTest {

    @Test
    public void testDestroyVMOrImageTask() throws Exception {
        DestroyVMOrImageTask task = new DestroyVMOrImageTask(
            client,
            "vm-438");
        task.process();
    }

}
