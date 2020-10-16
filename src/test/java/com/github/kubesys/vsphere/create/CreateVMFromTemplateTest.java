package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateVMFromTemplateTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_ac1d70599a1b28422a20d355fd4cd8d3; VSPHERE-UI-JSESSIONID=E6971060DBB818F5EBCFE59B766FB273; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_497db55573abe1ac47a36587d32619e3";
		String token = "c683fff5-bb2c-4c0c-8bca-efdfea63ddbc";
		System.out.println(getClient().virtualMachines().createFromTemplate("henry21", "vm-43", "group-v3", 
				"datastore-10", "test", cookie, token));
	}
}

