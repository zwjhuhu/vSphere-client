package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_398a14e9cf74fb2a29e8d104c4c84b8c; JSESSIONID=E84915ACC91D86155BF3D9BE6064E7CDBFFA; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_b7d785162db290db0d6aba13892ff4a1";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

