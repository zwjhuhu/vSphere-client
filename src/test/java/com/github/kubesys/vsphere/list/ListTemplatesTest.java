package com.github.kubesys.vsphere.list;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListTemplatesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_694ee092d1ab53947605a249407c1214; VSPHERE-UI-JSESSIONID=A7D77E05F8CC79FFC1BC6201122C35B4; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_13f93775b9dded9784bc2c36a40a4bdb";
		System.out.println(getClient().virtualMachinePools().listVMTemplates("Datacenter", cookie));
	}
}

