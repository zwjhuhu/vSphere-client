package com.github.kubesys.vsphere.list;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListTemplatesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		String cookie = vClient.getCookie();
		String token = vClient.getXSRFToken(cookie);
		JsonNode listVMTemplates = vClient.virtualMachinePools().listVMTemplates("Datacenter", cookie, token);
		System.out.println(listVMTemplates.get("data"));
		
	}
}

