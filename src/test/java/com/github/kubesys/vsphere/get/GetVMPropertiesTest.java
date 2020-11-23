package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMPropertiesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		System.out.println(vClient.virtualMachines().getVMProperties("centos7", vClient.getCookie()));
	}
}

