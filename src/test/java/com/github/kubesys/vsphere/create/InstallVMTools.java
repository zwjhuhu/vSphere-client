package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

public class InstallVMTools extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		String cookie = vClient.getCookie();
		String token = vClient.getXSRFToken(cookie);
		vClient.virtualMachines().installVMTools("xxxx", cookie, token);
	}

	
}
