package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateNetworkDvsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_1e25d9c38a25b646802185ed9be54393; VSPHERE-UI-JSESSIONID=1595B1887BAEE4E31EB80C2315AAAD4B; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_f275851a25186ff1ddfa0e0204607cea";
		String token = "98b413f8-7994-410a-a650-53668cdf6898";
		System.out.println(getClient().virtualMachineNetworks().createDvs("Datacenter", "henry222", cookie, token));
	}
}

