package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetResourcePoolInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_a3222d7ba9e53e4e4d1eb4b7b7f970c2; VSPHERE-UI-JSESSIONID=7B738674170F102C9C1992F1A47FCB84; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_f379ff8bd2659a899abef5d9ccfda872";
		System.out.println(getClient().virtualMachinePools().getResourcePoolInfo("test", cookie));
	}
}
