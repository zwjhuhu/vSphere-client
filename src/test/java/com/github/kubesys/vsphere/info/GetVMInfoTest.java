package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie= "CastleSessionvsphere.local=_cf3aba140b3904e4841655ef2f4e7530; JSESSIONID=870F9820F83F9F22EF4042928E5D8B47D281; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_b8dde344bb766b15e56e7b9ea2166ab8";
		System.out.println(getClient().virtualMachines().getVMInfo("luleyong", cookie));
	}

}
