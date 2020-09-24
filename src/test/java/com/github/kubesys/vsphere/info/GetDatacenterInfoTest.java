package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetDatacenterInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_ebf336b519e8afe4a05cd8c980212544; VSPHERE-UI-JSESSIONID=4865D16235CA1BF551FF08C54F8445EA; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_a058ceed40819a461e5bb8934d37a921";
		System.out.println(getClient().virtualMachinePools().getDataCenterInfo("Datacenter", cookie));
	}
}
