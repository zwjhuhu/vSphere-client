package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class CreateNetworkDvsTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_477dd7d50c031a8f8d78a6c6560f2789; JSESSIONID=EDD089C80CBD26BDCBE2731F9802EA93DFFC; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_49a73fe794aed142e94f6a56640c13c1";
		String token = "54427a74-5ad3-4ea4-a1d5-0854ce0f5678";
		System.out.println(getClient().virtualMachineNetworks().createDvs("测试数据中心", "henry1983", cookie, token));
	}
}

