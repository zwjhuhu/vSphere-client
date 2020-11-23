package com.github.kubesys.vsphere.list;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListComputingResourcesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		String cookie = vClient.getCookie();
		
		String dcName = "Datacenter";
		JsonNode dc = vClient.virtualMachines().searchUUID(dcName, "Datacenter", cookie);
		String dcId = dc.get(0).get("results").get(0).get("id").asText();
		JsonNode resList = vClient.virtualMachines().selectResource(dcId, cookie);
		
		for (int i = 0; i < resList.size(); i++) {
			JsonNode res = resList.get(i);
			System.out.println(res.get("text").asText());
		}
	}

}

