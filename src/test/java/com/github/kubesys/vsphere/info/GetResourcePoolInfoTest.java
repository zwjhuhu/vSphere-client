package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetResourcePoolInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_92d7f5a58c4f032fbb43f69d58cd55d5; VSPHERE-UI-JSESSIONID=E2823677DD028ECDB3139AC7510A91FB; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_4419896e678585cb49c02fba2c9c57c2";
		System.out.println(getClient().virtualMachinePools().getResourcePoolInfo("test", cookie));
	}
}
