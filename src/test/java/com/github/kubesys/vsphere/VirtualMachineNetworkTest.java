package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachineNetworkTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachineNetworks().createL3("datacenter-2", "9e4a98b3-189a-475b-b093-f5cda70cd2a5", "henry", "7491662C559E29C81763483CCEA8F527"));
	}
}

