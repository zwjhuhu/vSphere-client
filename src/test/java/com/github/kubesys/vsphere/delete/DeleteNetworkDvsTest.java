package com.github.kubesys.vsphere.delete;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class DeleteNetworkDvsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_b72807521d987291d8f2dd3bb2bc3766; VSPHERE-UI-JSESSIONID=D6CB8E9752CF767B7641190B0016834F; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_4f2ffcdaa3ba0a198f352e2ccc329952";
		System.out.println(getClient().virtualMachineNetworks().deleteDvs("henry", cookie));
	}
}

