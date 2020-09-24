/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kubesys.vsphere.VsphereClient;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public abstract class AbstractImpl {

	/**
	 * client
	 */
	protected final VsphereClient client;

	public AbstractImpl(VsphereClient client) {
		super();
		this.client = client;
	}

	protected Headers getHeaders(String cookie) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
		if (cookie != null) {
			map.put("Cookie", cookie + "; vmware-api-session-id=" + client.getSession());
		} else {
			map.put("Cookie", "vmware-api-session-id=" + client.getSession());
		}
		return Headers.of(map);
	}

	protected JsonNode listWithoutCookie(String url) throws Exception {
		Request request = new Request.Builder()
				.url(url)
				.headers(getHeaders(null))
				.method("GET", null)
				.build();
		return new ObjectMapper().readTree(client.getHttpClient().newCall(request).execute().body().byteStream());
	}
	
	protected JsonNode getWithoutCookie(String url) throws Exception {
		Request request = new Request.Builder()
				.url(url)
				.headers(getHeaders(null))
				.method("GET", null)
				.build();
		return new ObjectMapper().readTree(client.getHttpClient().newCall(request).execute().body().byteStream());
	}
	

	@SuppressWarnings("deprecation")
	protected JsonNode postWithoutCookie(String url) throws Exception {
		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
				.url(url)
				.headers(getHeaders(null))
				.method("POST", body)
				.build();
		return new ObjectMapper().readTree(client.getHttpClient().newCall(request).execute().body().byteStream());
	}

	protected JsonNode remove(String url) throws Exception {
//		HttpEntity<String> getReq = new HttpEntity<>("", getDefHttpHeaders());
//		return new RestTemplate().exchange(url, HttpMethod.DELETE, getReq, JsonNode.class).getBody();
		return null;
	}

	protected JsonNode patch(String url, String body) throws Exception {
//		HttpEntity<String> getReq = new HttpEntity<>(body, getDefHttpHeaders());
//		CloseableHttpClient httpClient = HttpClientBuilder.create()
//				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
//				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
//		return restTemplate.exchange(url, HttpMethod.PATCH, getReq, JsonNode.class).getBody();
		return null;
	}
	

	protected JsonNode listWithCookie(String url, String cookie) throws Exception {
		Request request = new Request.Builder()
				.url(url)
				.headers(getHeaders(cookie))
				.method("GET", null)
				.build();
		ResponseBody body = client.getHttpClient().newCall(request).execute().body();
		return new ObjectMapper().readTree(body.byteStream());

	}

	protected JsonNode post(String url, String body, String jsessionId) throws Exception {
//		HttpEntity<String> postReq = new HttpEntity<>(body, getUIHttpHeaders(jsessionId));
//		return new RestTemplate().exchange(url, HttpMethod.POST, postReq, JsonNode.class).getBody();
		return null;
	}

	protected JsonNode get(String url, String jsessionId) throws Exception {
//		HttpEntity<String> getReq = new HttpEntity<>("", getUIHttpHeaders(jsessionId));
//		System.out.println(url);
//		return new RestTemplate().exchange(url, HttpMethod.GET, getReq, JsonNode.class).getBody();
		return null;
	}

	public String searchRealname(String name, String type, String jsessionId) {

		try {
			String clusterIdUrl = this.client.getUrl() + "/ui/search/quicksearch/?opId=0&query=" + name;

			JsonNode objects = listWithCookie(clusterIdUrl, jsessionId);

			int objList = objects.size();

			for (int i = 0; i < objList; i++) {
				JsonNode obj = objects.get(i);
				if (obj.get("label").asText().equals(type)) {

					JsonNode results = obj.get("results");

					int resList = results.size();

					for (int j = 0; j < resList; j++) {

						JsonNode res = results.get(j);

						if (res.get("name").asText().equals(name)) {

							return res.get("id").asText();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode info(String id, String model, String cookie) throws Exception {
		return listWithCookie(this.client.getUrl() + "/ui/data/" 
							+ id + "?model=" + model, cookie);
	}
	
	public JsonNode search(String name, String type, String cookie) {

		try {
			String clusterIdUrl = this.client.getUrl() + "/ui/search/quicksearch/?opId=0&query=" + name;

			JsonNode objects = listWithCookie(clusterIdUrl, cookie);

			int objList = objects.size();

			for (int i = 0; i < objList; i++) {
				JsonNode obj = objects.get(i);
				if (obj.get("label").asText().equals(type)) {

					JsonNode results = obj.get("results");

					int resList = results.size();

					for (int j = 0; j < resList; j++) {

						JsonNode res = results.get(j);

						if (res.get("name").asText().equals(name)) {

							return res;
						}
					}
					
					return results.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
