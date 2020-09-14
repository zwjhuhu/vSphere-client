package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class ListNetworksTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().listNetworks());
	}
}

