package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VirtualMachinePoolTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(client.virtualMachinePool().listDataCenters());
//		System.out.println(client.virtualMachinePool().listDataStores());
//		System.out.println(client.virtualMachinePool().listNetworks());
		System.out.println(client.virtualMachinePool().listClusters());
		System.out.println(client.virtualMachinePool().listFolders());
//		System.out.println(client.virtualMachinePool().listHosts());
		System.out.println(client.virtualMachinePool().listVMs());
//		System.out.println(client.virtualMachinePool().getJessionId());
		System.out.println(client.virtualMachinePool().getClusterInfo("Test123", "88BC28B6A38E35F9772605D96E43DAF1"));
	}
}

