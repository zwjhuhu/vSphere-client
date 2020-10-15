package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetTemplateInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_24fc34f39fd6d6a02d1260dc4538c4de; VSPHERE-UI-JSESSIONID=8156FF5BD8E75D990AC203C9AC5910CE; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_dee067cfe9908f4742d347375e306d43";
		System.out.println(getClient().virtualMachines().getVMTemplateInfo("ooo", cookie));
	}
}
