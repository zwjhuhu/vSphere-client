package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetResourcePoolTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getResourcePool("resgroup-17"));
	}
}

