package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class GetBase64Test extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(VsphereClient.getBase64Creds(
				"administrator@vsphere.test", "Onceas2020!234"));
	}
}

