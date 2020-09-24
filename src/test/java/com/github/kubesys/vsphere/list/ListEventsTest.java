package com.github.kubesys.vsphere.list;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListEventsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_3cd06fbf351b50613a48cb2add80d804; VSPHERE-UI-JSESSIONID=573D4BC1F00A4CAE3BF2185560A8ECC9; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_eead882b095c989d5fcb43231f22bbee";
		System.out.println(getClient().virtualMachinePools().listEvents(1, cookie));
	}
}

