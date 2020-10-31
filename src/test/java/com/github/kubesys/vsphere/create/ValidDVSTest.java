package com.github.kubesys.vsphere.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ValidDVSTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		JsonNode uuid = vClient.virtualMachines().searchUUID("DSwitch2", "DistributedVirtualSwitch", vClient.getCookie());
		System.out.println(vClient.virtualMachines().validDVS("urn:vmomi:VmwareDistributedVirtualSwitch:dvs-348:c0d6264c-3d65-4183-b3f3-516f4cb5fee0", vClient.getCookie()));
	}

}
