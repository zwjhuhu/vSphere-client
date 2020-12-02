package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import cn.com.skywin.cloudadmin.vsphere.task.DeleteFileTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class DeleteFileTaskTest extends BaseTaskTest {

    @Test
    public void testDeleteFileTask() throws Exception {
        DeleteFileTask task = new DeleteFileTask(
            client,
            "datacenter-2","datastore-19","[Datastore2] volumes/042763d5eda644fb8f2212481afdd170/042763d5eda644fb8f2212481afdd170.vmdk");
        task.process();
    }

}
