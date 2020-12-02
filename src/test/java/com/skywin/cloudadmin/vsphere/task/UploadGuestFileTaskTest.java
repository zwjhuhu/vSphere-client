 package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.UploadGuestFileTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class UploadGuestFileTaskTest  extends BaseTaskTest {
    
    @Test
    public void testUploadGuestFile() throws Exception {
        UploadGuestFileTask task = new UploadGuestFileTask(
            client,
            "/tmp/aa", "d:/1.txt", "vm-381", "vyos", "vyos");
        task.process();
    }

}
