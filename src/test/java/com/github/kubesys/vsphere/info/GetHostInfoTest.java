package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetHostInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_afc9739a61621f24af941838e4e8f5f1; VSPHERE-UI-JSESSIONID=385658BDD246C93A430119FDDA962424; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_446f2b2d8a08bab29ef9688e5b6dc437; ";
		System.out.println(getClient().virtualMachinePools().getHostInfo("133.133.135.33", cookie));
	}
}
