package com.github.kubesys.vsphere.list;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListVMEventsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_785c0fccaf7ff357944b66b04e4f6b21; VSPHERE-UI-JSESSIONID=13C0677C7357FFE6589193DF213E2864; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_d80a691898b7fc1d0db92b528d4d1588";
		System.out.println(getClient().virtualMachines().listVMEvents("centos7", cookie));
	}
}

