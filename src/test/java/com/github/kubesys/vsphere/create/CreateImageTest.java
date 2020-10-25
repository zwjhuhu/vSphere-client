package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateImageTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_2e8b59052d461d81a377866dd41ebcce; VSPHERE-UI-JSESSIONID=BE8B8D138E5065D452C93F9F19130CED; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_744229e856cb0ba8fbe14e0b46b6047c";
		String token = "52e2521f-b1e6-469d-a7b4-e2554dd259d7";
		System.out.println(getClient().virtualMachines().createImageFromVM("henry20", cookie, token));
	}
}

