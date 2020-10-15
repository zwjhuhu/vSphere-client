package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_b6b71090743b9c580f46067a93692485; VSPHERE-UI-JSESSIONID=837D6ABD71BBC8F2CCCFD419E87513DB; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_098f2274ef6b2a29f1384c618769f99f";
		System.out.println(getClient().virtualMachines().getVMInfo("CentOS7", cookie));
	}

}
