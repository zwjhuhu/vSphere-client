package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetDatacenterInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_33f763a3a84c94845397c1ab3717266e; VSPHERE-UI-JSESSIONID=0AE116BD5FD719F55A368B8FC836ED11; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_ce57c960398175070c31ff9f86523a85; VSPHERE-UI-XSRF-TOKEN=458d92d0-3d10-4042-aca2-6cc161a52a33";
		System.out.println(getClient().virtualMachinePools().getDataCenterInfo("Datacenter", cookie));
	}
}
