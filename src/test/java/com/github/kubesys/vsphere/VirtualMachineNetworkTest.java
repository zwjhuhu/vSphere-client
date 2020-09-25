package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachineNetworkTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(client.virtualMachineNetworks().deleteDvs("DSwitch", "C8E29C70219A2CF756F1B8D21B43C668"));
		System.out.println(client.virtualMachineNetworks().createDvs("datacenter-2", "henry", "C8E29C70219A2CF756F1B8D21B43C668"));
	}
}

