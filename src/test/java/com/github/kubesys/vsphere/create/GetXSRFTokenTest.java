package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_67db064437343ec899656f74eaf128ee; VSPHERE-UI-JSESSIONID=4698C49AC96ABF52AD39FF96D516F5A4; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_f47af0847236bb4bd828bd5675155f0e";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

