package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachineTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(client.virtualMachinePool().listVMs());
//		System.out.println(client.virtualMachines().getVMInfo("vm-12"));
//		System.out.println(client.virtualMachines().getImageInfo("vm-13"));
//		System.out.println(client.virtualMachines().stop("vm-43"));
		System.out.println(client.virtualMachines().reset("vm-21"));
		System.out.println(client.virtualMachines().delete("vm-21"));
		System.out.println(client.virtualMachines().suspend("vm-21"));
		System.out.println(client.virtualMachines().updateCPU("vm-21", 2));
		System.out.println(client.virtualMachines().updateRAM("vm-21", 4096));
	}
}

