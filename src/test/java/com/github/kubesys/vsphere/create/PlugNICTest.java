package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class PlugNICTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(getClient().virtualMachines().plugVM_STD_NIC("vm-250", "network-11"));
		System.out.println(getClient().virtualMachines().plugVM_DVS_NIC("vm-329", "dvportgroup-346"));
	}
}

