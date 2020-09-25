package com.github.kubesys.vsphere.list;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListVMsInHostTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachinePools().listVMsInHost("host-9"));
	}
}

