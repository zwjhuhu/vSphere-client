package com.github.kubesys.vsphere.list;


import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListTemplatesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_cb3ef0f238e008e57794c12eace1e815; VSPHERE-UI-JSESSIONID=D71636D170A7790411070C53083BE4BB; VSPHERE-UI-XSRF-TOKEN=7f710599-2cf6-4b47-b7b2-6f423ebfb1af; style=light; CastleSessionvsphere.test=_4c212205771912f765561b679f7ebc69";
		String token = "7f710599-2cf6-4b47-b7b2-6f423ebfb1af";
		System.out.println(getClient().virtualMachinePools().listVMTemplates("Datacenter", cookie, token));
		
	}
}

