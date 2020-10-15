package com.github.kubesys.vsphere.list;


import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class ListTemplatesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_b828fd66894edfc46f441334c7d7afe9; VSPHERE-UI-JSESSIONID=CE736EFF53517DF19559ECFE81FF9046; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_963fd26153647507824a18314a89bcee";
		String token = "3d6e7fee-9b53-47fd-9637-186148ff2baa";
		System.out.println(getClient().virtualMachinePools().listVMTemplates("Datacenter", cookie, token));
		
	}
}

