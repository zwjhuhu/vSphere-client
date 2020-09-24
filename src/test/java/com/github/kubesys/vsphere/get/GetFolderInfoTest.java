package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetFolderInfoTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachinePools().search("vm", "虚拟机", "6644BA4503B13E67275CC04540A6FF43"));
	}
}

