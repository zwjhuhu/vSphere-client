package com.github.kubesys.vsphere.info;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetHostInfoTest extends VsphereClientTest {

	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_cefdbcb552a5e44b9ec9896cb9dcb691; VSPHERE-UI-JSESSIONID=7B4E891A21231360E099C015F11B8707; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_d1ee24fc8fcdf61ed65d87f213710e2c";
		System.out.println(getClient().virtualMachinePools().getHostInfo("133.133.135.33", cookie));
	}
}
