package com.github.kubesys.vsphere.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class loginUrlTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		JsonNode jsonNode = getClient().loginUrl();
		System.out.println(getClient().getKeyInHeader("Set-Cookie", jsonNode));
		System.out.println(getClient().getKeyInHeader("Location", jsonNode));
	}
}

