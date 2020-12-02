package com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto;
import cn.com.skywin.cloudadmin.vsphere.task.MigrateVmTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class MigrateVmTaskTest extends BaseTaskTest {

    @Test
    public void testMigrateVmTask() throws Exception {

        List<String> availDatastores = new ArrayList<>();
        availDatastores.add("datastore-19");
        List<String> availHosts = new ArrayList<>();
        availHosts.add("host-17");
        List<String> networks = new ArrayList<>();
        networks.add("network-128");
        MigrateVmTask task = new MigrateVmTask(client, 
            "vm-402", "host-17",null);
        CreatedOrMigratedVmInfoDto dto = task.process();
        System.out.println(JSON.toJSONString(dto));
    }

}
