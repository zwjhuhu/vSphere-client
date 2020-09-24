package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetBase64Test extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(VsphereClient.getBase64Creds(
				"administrator@vsphere.test", "Onceas2020!234"));
	}
}

