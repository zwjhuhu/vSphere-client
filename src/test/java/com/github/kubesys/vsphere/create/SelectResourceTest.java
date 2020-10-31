package com.github.kubesys.vsphere.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class SelectResourceTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_d5a0328e6e79f63ec29f4ca5a2ceb0d6; VSPHERE-UI-JSESSIONID=E6BAE73166209D01C1B8F08E4178FE8F; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_fb314e13d34fa59db89788cca9f0fafa";
		VsphereClient vClient = getClient();
		String dcName = "Datacenter";
		JsonNode dc = vClient.virtualMachines().searchUUID(dcName, "Datacenter", cookie);
		String dcId = dc.get(0).get("results").get(0).get("id").asText();
		
		System.out.println(dcName);
		
		JsonNode resList = vClient.virtualMachines().selectResource(dcId, cookie);
		extracted(vClient, resList, "\t", cookie);
	}

	protected static void extracted(VsphereClient vClient, JsonNode resList, String spacing, String cookie) throws Exception {
		for (int i = 0; i < resList.size(); i++) {
			JsonNode res = resList.get(i);
			System.out.println(spacing + res.get("text").asText());
			if (res.get("hasChildren").asBoolean() == true) {
				String type = res.get("nodeTypeId").asText();
				String uuid = res.get("objRef").asText();
				JsonNode list = vClient.virtualMachines().selectResource(type, uuid, cookie);
				extracted(vClient, list, "\t" + spacing, cookie);
			} 
		}
	}
}

