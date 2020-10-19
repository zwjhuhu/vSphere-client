package com.github.kubesys.vsphere.search;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class SearchUUID extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_934cd815b8983931ea74c2b2d7d69217; VSPHERE-UI-JSESSIONID=CFF867C76CA8472EA4D2773CBDEDDC42; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_8065fa7c071608238dd628665a14ea05";
		// type can be "Virtual Machine, VM Template, Cluster, Datacenter, Datastore, Resource Pool, Host"
		System.out.println(getClient().virtualMachinePools().searchUUID("test", "Resource Pool", cookie));
	}

}
