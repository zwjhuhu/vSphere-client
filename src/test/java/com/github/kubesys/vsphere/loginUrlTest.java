package com.github.kubesys.vsphere;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Unit test for simple App.
 */
public class loginUrlTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		JsonNode jsonNode = client.loginUrl().get("headers");
		System.out.println(jsonNode.get("Set-Cookie").toPrettyString());
		System.out.println(jsonNode.get("Location").get(0).asText());
	}
}

