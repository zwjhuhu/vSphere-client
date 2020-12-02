package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.com.skywin.cloudadmin.vsphere.task.FindVmInfosByNameExTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class FindVmInfosByNameExTaskTest extends BaseTaskTest {

    @Test
    public void testFindVmInfosByNameExTask() throws Exception {
        FindVmInfosByNameExTask task = new FindVmInfosByNameExTask(
            client,
            "vm-344");
        System.out.println(JSON.toJSONString(task.process()));
    }

}
