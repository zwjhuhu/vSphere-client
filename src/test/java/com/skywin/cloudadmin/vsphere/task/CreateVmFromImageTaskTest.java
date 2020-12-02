package com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.com.skywin.cloudadmin.api.vsphere.dto.CreatedOrMigratedVmInfoDto;
import cn.com.skywin.cloudadmin.vsphere.task.CreateVmFromImageTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class CreateVmFromImageTaskTest extends BaseTaskTest {

    @Test
    public void testCreateVmFromImageTask() throws Exception {

        List<String> availDatastores = new ArrayList<>();
        availDatastores.add("datastore-19");
        List<String> availHosts = new ArrayList<>();
        availHosts.add("host-17");
        List<String> networks = new ArrayList<>();
        networks.add("network-128");
        CreateVmFromImageTask task = new CreateVmFromImageTask(client, 
            "vm-402", "c4", "datacenter-2", availDatastores,
            availHosts, networks, 1024L*1024*1024, null, 6500,true);
        CreatedOrMigratedVmInfoDto dto = task.process();
        System.out.println(JSON.toJSONString(dto));
    }

}
