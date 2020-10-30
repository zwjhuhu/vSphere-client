package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_934e58fa1a301c3707001f87aad3a050; VSPHERE-UI-JSESSIONID=969048449DF195774AC04B173FBB9409; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_a8c5ec1252c4957bdbf3a6dbc1c493b8";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

