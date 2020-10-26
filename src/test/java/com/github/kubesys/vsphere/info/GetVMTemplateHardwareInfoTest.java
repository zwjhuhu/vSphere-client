package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMTemplateHardwareInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie= "CastleSessionvsphere.local=_ec864fd67563a0be0f96c833805fcbd9; JSESSIONID=1BFEF1D84F81C18737284239E6C3D94F7EC8; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_427ee4b26b983f5c0bf70387d837f237";
		System.out.println(getClient().virtualMachines().getVMTemplateHardwareInfo("部署服务器", cookie));
	}

}
