package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetClusterInfoTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getClusterInfo("Test123", "61D26319A0D2D715AB4325260DF38671"));
		
	}
	
}

