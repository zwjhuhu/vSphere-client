package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachineNetworkTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(client.virtualMachineNetworks().deleteL3("DSwitch", "C8E29C70219A2CF756F1B8D21B43C668"));
		System.out.println(client.virtualMachineNetworks().createL3("datacenter-2", "9e4a98b3-189a-475b-b093-f5cda70cd2a5", "henry", "C8E29C70219A2CF756F1B8D21B43C668"));
	}
}

