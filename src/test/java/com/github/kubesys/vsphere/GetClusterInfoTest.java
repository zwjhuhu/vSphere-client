package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetClusterInfoTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getClusterInfo("Test123", "6644BA4503B13E67275CC04540A6FF43"));
	}
}

