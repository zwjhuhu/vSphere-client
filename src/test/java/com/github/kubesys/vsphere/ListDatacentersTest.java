package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class ListDatacentersTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().listDataCenters());
	}
}

