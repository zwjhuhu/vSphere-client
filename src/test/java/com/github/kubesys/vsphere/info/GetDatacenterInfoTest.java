package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetDatacenterInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_c006a4ce0b63d3a810113b84df1d2f5f; VSPHERE-UI-JSESSIONID=962E562B8CA1C63DAA142272A1503C0E; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_9a569ef79b2528a39e26037d2b626488";
		System.out.println(getClient().virtualMachinePools().getDataCenterInfo("Datacenter", cookie));
	}
}
