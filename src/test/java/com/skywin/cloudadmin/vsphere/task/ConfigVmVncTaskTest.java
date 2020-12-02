package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.ConfigVmVncTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class ConfigVmVncTaskTest extends BaseTaskTest {

    @Test
    public void testConfigVmVncTaskTask() throws Exception {
        ConfigVmVncTask task = new ConfigVmVncTask(
            client,
            "vm-344",5910);
        task.process();
    }

}
