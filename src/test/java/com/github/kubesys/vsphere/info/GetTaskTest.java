package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetTaskTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_faf28222e63451fe575fd7dfcb9a2ed5; JSESSIONID=53FF7CA3FB785B0510116D1B0BC79103077E; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_d54cce566d6ea131230dc05f29c0e7b8";
		System.out.println(getClient().virtualMachines().getTask("task-7246", cookie).get("state").asText());
	}
}
