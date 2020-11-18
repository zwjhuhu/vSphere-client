package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetClusterInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		System.out.println(vClient.virtualMachinePools().getClusterInfo("新建群集1", vClient.getCookie()));
	}

}
