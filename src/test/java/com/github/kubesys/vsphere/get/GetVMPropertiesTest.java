package com.github.kubesys.vsphere.get;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class GetVMPropertiesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_a7980b1835bff5de2d15babdf9e9b264; JSESSIONID=800BACC7C1A92AC95181E3783CFBEC02CBA3; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_4721273ae2f9ce541f711664a5da0c20";
		String token = "19382f47-6f0b-4e9f-8e40-55be40cba14e";
		System.out.println(getClient().virtualMachines().getVMProperties("cnetos", cookie));
	}
}

