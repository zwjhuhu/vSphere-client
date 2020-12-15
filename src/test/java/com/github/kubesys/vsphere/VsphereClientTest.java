package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VsphereClientTest {

	public static VsphereClient client;

	public static VsphereClient getClient() throws Exception {
		if (client == null) {
//			client = new VsphereClient("https", "133.133.135.35", 443, "administrator@vsphere.test", "Onceas2020!234", "6.7");
			client = new VsphereClient("https", "192.168.23.148", 443, "administrator@vsphere.local", "Qwe@1234", "6.5");
		}
		return client;
	}

}
