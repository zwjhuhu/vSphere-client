package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class VsphereClientTest {

	public static VsphereClient client;

	public static VsphereClient getClient() throws Exception {
		return new VsphereClient("https", "133.133.135.35", 443, "administrator@vsphere.test", "Onceas2020!234", "6.7");
	}

}
