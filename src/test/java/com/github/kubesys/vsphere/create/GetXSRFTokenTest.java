package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_a508022e065a2d5503343de398632031; VSPHERE-UI-JSESSIONID=B681433D517C97FC2BE8760E5F26C013; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_8795d65ad8206903ef6b688c6322a663";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

