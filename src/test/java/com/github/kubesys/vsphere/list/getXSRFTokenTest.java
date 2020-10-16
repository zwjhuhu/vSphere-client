package com.github.kubesys.vsphere.list;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class getXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient httpClient = getClient();
		String cookie = httpClient.getCookie();
		System.out.println(cookie);
		System.out.println(httpClient.getXSRFToken(cookie));
	}
}

