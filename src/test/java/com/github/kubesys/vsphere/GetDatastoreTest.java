package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetDatastoreTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachinePools().getDataStore("datastore-10"));
	}
}

