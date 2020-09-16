package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class ListResourcePoolsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().listResourcePools());
	}
}

