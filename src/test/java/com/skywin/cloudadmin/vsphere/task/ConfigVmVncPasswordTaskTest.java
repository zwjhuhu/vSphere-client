package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.ConfigVmVncPasswordTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class ConfigVmVncPasswordTaskTest extends BaseTaskTest {

    @Test
    public void testConfigVmVncTaskTask() throws Exception {
        ConfigVmVncPasswordTask task = new ConfigVmVncPasswordTask(
            client,"vm-402","xxx");
        task.process();
    }

}
