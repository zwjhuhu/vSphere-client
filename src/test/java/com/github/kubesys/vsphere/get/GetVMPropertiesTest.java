package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMPropertiesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_29254cdf201642054fe2fa007e843c7b; JSESSIONID=86BBED58D95F5D20F2946D749BA8EEF6A0E6; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_bf95cc74a41785e4ba5ef224db9f11d0";
		String token = "19382f47-6f0b-4e9f-8e40-55be40cba14e";
		System.out.println(getClient().virtualMachines().getVMProperties("cnetos", cookie));
	}
}

