package com.skywin.cloudadmin.vsphere.task;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.com.skywin.cloudadmin.api.vsphere.info.template.UploadTemplateDto;
import cn.com.skywin.cloudadmin.commons.UUIDGenerator;
import cn.com.skywin.cloudadmin.vsphere.task.CreateImageTask;

/**
 * @author zwj
 * @date 2020/11/23
 */
public class CreateImageTaskTest  extends BaseTaskTest {

    @Test
    public void testCreateImageTask() throws Exception {
        CreateImageTask task = new CreateImageTask(
            client,
            UUIDGenerator.gen(), "test1", "linux", "datacenter-2",
            "datastore-19", "D:/1.txt");
        UploadTemplateDto dto = task.process();
        System.out.println(JSON.toJSONString(dto));
    }

}
