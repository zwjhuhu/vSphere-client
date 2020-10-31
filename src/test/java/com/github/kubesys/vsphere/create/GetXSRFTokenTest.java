package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_d6e6901d795edae3154dcf29b8e35079; JSESSIONID=79430D594CCD18EBFB1DED2548052D9A793C; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_a1f5f89ec4db0700dc516fe3fc40a2fd";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

