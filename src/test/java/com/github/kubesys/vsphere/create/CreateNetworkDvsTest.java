package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateNetworkDvsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_f6249d405b53d3e3e4dedb40b3401767; VSPHERE-UI-JSESSIONID=F0FCF4C9CF78CB18D59AC432A2628048; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_cc79cac0f5dbcd2b7dfb7b7b06cc476a; VSPHERE-UI-XSRF-TOKEN=8ba1edf4-7a87-4d01-97f1-5e6f0fbaaf3a";
		System.out.println(getClient().virtualMachineNetworks().createDvs("Datacenter", "henry", cookie));
	}
}

