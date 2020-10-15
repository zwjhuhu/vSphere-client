package com.github.kubesys.vsphere.post;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class UpdateVMCPUTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachines().updateCPU("vm-21", 4));
	}
}

