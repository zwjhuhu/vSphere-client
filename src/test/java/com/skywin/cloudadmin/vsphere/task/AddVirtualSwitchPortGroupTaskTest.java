package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.AddVirtualSwitchPortGroupTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class AddVirtualSwitchPortGroupTaskTest extends BaseTaskTest {

    @Test
    public void testAddVirtualSwitchPortGroupTask() throws Exception {

        AddVirtualSwitchPortGroupTask task = new AddVirtualSwitchPortGroupTask(
            client,"host-25","test","vSwitch0",0);
        System.out.println(task.process());
    }

}
