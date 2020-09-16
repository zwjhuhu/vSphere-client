package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetClusterInfoTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getClusterInfo("Test123", "82F951F8351C42185619B3FB0FA52E7E"));
	}
}

