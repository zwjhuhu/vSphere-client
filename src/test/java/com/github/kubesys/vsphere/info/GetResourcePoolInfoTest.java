package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetResourcePoolInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_8d90d5f092940e41a5b037808f66d2d4; VSPHERE-UI-JSESSIONID=B8CF2CADACD55256AA68E0AE9DA8A22C; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_d288f21b26045f67b7114a90dbe43457";
		System.out.println(getClient().virtualMachinePools().getResourcePoolInfo("test", cookie));
	}
}
