package com.github.kubesys.vsphere.delete;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class DeleteVMNICTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachines().unplugVMNIC("vm-21", "4001"));
	}
}

