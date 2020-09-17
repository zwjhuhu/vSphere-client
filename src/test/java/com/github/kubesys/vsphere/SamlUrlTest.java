package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class SamlUrlTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String url = client.loginUrl().get("headers").get("Location").get(0).asText();
		String token = VsphereClient.getBase64Creds("administrator@vsphere.test", "Onceas2020!234");
		System.out.println(url);
		System.out.println(token);
		System.out.println(client.samlUrl(url, token));
	}
}

