package com.github.kubesys.vsphere.delete;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class DeleteVMDiskTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachines().deleteVMDisk("vm-21", "2001"));
	}
}

