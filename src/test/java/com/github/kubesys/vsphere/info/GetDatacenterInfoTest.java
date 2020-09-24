package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetDatacenterInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_cf2d842754d024b850d1995b2ad5e24c; VSPHERE-UI-JSESSIONID=8FBA6F5761433E9EE20F3F64ACE89FB0; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_c891fb42085d99e66e4c8fe5039d84ab";
		System.out.println(getClient().virtualMachinePools().getDataCenterInfo("Datacenter", cookie));
	}
}
