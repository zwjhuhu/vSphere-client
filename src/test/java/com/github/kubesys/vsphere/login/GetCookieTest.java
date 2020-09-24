package com.github.kubesys.vsphere.login;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetCookieTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().getCookie());
	}
}

