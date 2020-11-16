package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetXSRFTokenTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		System.out.println(vClient.getXSRFToken(vClient.getCookie()));
	}
}

