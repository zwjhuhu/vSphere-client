package com.github.kubesys.vsphere;

import com.fasterxml.jackson.databind.JsonNode;

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

