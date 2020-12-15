package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.RemoveVirtualSwitchPortGroupTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class RemoveVirtualSwitchPortGroupTaskTest extends BaseTaskTest {

    @Test
    public void testAddVirtualSwitchPortGroupTask() throws Exception {

        RemoveVirtualSwitchPortGroupTask task = new RemoveVirtualSwitchPortGroupTask(
            client,"network-41");
        System.out.println(task.process());
    }

}
