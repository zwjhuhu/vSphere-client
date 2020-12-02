package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.commons.UUIDGenerator;
import cn.com.skywin.cloudadmin.vsphere.task.CreateDiskFromImageTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class CreateDiskFromImageTaskTest  extends BaseTaskTest {

    @Test
    public void testCreateDiskFromImageTask() throws Exception {
        CreateDiskFromImageTask task = new CreateDiskFromImageTask(
            client,
            "datacenter-2","datacenter-2","[Datastore2] images/677e5b6db1374e70872b876d77a73d38/1.vmdk",
            "datastore-19","datastore-19", UUIDGenerator.gen());
        task.process();
    }

}
