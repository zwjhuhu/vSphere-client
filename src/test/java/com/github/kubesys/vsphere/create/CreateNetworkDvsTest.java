package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateNetworkDvsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_37c5128ecba0f551eb41458aeafd89a9; VSPHERE-UI-JSESSIONID=7D7EF1B52BE98795E1429F4540E193E2; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_cfbbd5edf933091202d31d4f67518b77";
		System.out.println(getClient().virtualMachineNetworks().createDvs("Datacenter", "henry", cookie));
	}
}

