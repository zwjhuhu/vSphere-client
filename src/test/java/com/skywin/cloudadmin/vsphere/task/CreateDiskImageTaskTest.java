package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.commons.UUIDGenerator;
import cn.com.skywin.cloudadmin.vsphere.task.CreateDiskImageTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class CreateDiskImageTaskTest extends BaseTaskTest {

    @Test
    public void testCreateDiskImageTask() throws Exception {
        CreateDiskImageTask task = new CreateDiskImageTask(
            client,
            UUIDGenerator.gen(), "datacenter-2","datastore-19", "D:/1.txt");
        String path = task.process();
        System.out.println(path);
    }

}
