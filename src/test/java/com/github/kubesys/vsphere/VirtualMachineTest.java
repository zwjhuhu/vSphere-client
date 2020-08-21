package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachineTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePool().listVMs());
//		System.out.println(client.virtualMachines().getVMInfo("vm-12"));
//		System.out.println(client.virtualMachines().getImageInfo("vm-13"));
		System.out.println(client.virtualMachines().stop("vm-43"));
	}
}

