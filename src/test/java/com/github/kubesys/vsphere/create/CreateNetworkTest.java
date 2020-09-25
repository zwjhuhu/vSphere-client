package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateNetworkTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(client.virtualMachines().createFromTemplate("henry", "vm-43", "group-v3", 
				"datastore-10", "resgroup-17", "9e4a98b3-189a-475b-b093-f5cda70cd2a5", "6644BA4503B13E67275CC04540A6FF43"));
	}
}

