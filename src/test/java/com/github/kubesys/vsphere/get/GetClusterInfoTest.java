package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetClusterInfoTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getClusterInfo("Test123", "D54653702ED4D25AAC213D7E2DA9EEC0"));
		
	}
	
}

