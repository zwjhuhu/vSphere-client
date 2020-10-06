package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_ee5ad84d1aba72760b4845d10dad275e; VSPHERE-UI-JSESSIONID=E1F3D649AE02D8D7EDF1317885DBA106; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_f37cba09b694b7f5e74336cdbb7d9375";
		System.out.println(getClient().virtualMachines().getVMInfo("CentOS7", cookie));
	}

}
