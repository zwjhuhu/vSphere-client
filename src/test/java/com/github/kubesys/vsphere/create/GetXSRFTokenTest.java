package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_2d05aa0da587093a49c98dd3f1b82e1f; JSESSIONID=57F44F0ABC2A4D9CB1755EC117D3B60B8705; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_04877847fca7979a00e6d537db080174";
		System.out.println(getClient().getXSRFToken(cookie));
	}
}

