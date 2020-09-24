package com.github.kubesys.vsphere.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

import okhttp3.Response;

/**
 * Unit test for simple App.
 */
public class WebSSOTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient httpClient = getClient();
		JsonNode jsonNode = httpClient.loginUrl();
		String cookie = httpClient.getKeyInHeader("Set-Cookie", jsonNode);
		String url = httpClient.getKeyInHeader("Location", jsonNode);
		System.out.println(url);
		System.out.println(cookie);
		Response resp = httpClient.saml2Url(url, cookie);
		System.out.println(resp.headers());
		Response webssoUrl = httpClient.webssoUrl(
				resp.header("Set-Cookie"), 
				httpClient.getSAMLResponse(resp.body().byteStream()));
		System.out.println(new ObjectMapper().writeValueAsString(webssoUrl.headers()));
	}
}

