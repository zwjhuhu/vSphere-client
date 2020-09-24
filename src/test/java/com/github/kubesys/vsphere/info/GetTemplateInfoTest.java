package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetTemplateInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().search("ooo", "虚拟机模板", "6644BA4503B13E67275CC04540A6FF43"));
	}
}
