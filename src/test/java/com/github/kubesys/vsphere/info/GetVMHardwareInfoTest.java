package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMHardwareInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie= "CastleSessionvsphere.local=_e7ff372370fc04ab0904760944cd5876; JSESSIONID=BA334307A40FE0368B1BE28399199F282EC3; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_f770943e0f10b75d878ca8a89077a4ad";
		System.out.println(getClient().virtualMachines().getVMHardwareInfo("vcenter", cookie));
	}

}
