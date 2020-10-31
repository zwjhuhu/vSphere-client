package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class UnplugNICTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachines().unplugVMNIC("vm-329", "4002"));
	}
}

