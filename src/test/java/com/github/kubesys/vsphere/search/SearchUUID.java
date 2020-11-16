package com.github.kubesys.vsphere.search;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class SearchUUID extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		String cookie = vClient.getCookie();
		System.out.println(getClient().virtualMachinePools().search("test", "Resource Pool", cookie));
	}

}
