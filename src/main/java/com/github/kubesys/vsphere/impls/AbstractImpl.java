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
import okhttp3.Request;

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

//	protected HttpHeaders getDefHttpHeaders() throws Exception {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Accept", "application/json");
//		headers.add("Content-Type", "application/json");
//		if (client.getSession() != null) {
//			headers.add("Cookie", "vmware-api-session-id=" + client.getSession());
//		}
//		return headers;
//	}
//
//	protected HttpHeaders getUIHttpHeaders(String jsessionId) throws Exception {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Accept", "application/json");
//		if (client.getSession() != null) {
//			if (VsphereClient.VERSION.equals("6.7")) {
//				headers.add("Cookie", "VSPHERE-USERNAME=" + client.getUsername() + ";VSPHERE-UI-JSESSIONID=" + jsessionId);
//			} else if (VsphereClient.VERSION.equals("6.5")) {
//				headers.add("Cookie", "VSPHERE-USERNAME=" + client.getUsername() + ";JSESSIONID=" + jsessionId);
//			}
//		}
//		return headers;
//	}

	protected Headers getDefHeaders() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
		map.put("Cookie", "vmware-api-session-id=" + client.getSession());
		return Headers.of(map);
	}

	protected JsonNode list(String url) throws Exception {
		Request request = new Request.Builder()
				.url(url)
				.headers(getDefHeaders())
				.method("GET", null)
				.build();
		return new ObjectMapper().readTree(client.getHttpClient().newCall(request).execute().body().byteStream());
	}


	protected JsonNode post(String url) throws Exception {
//		HttpEntity<String> getReq = new HttpEntity<>("", getDefHttpHeaders());
//		return new RestTemplate().exchange(url, HttpMethod.POST, getReq, JsonNode.class).getBody();
		return null;
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
	

	protected JsonNode list(String url, String jsessionId) throws Exception {
//		HttpEntity<String> getReq = new HttpEntity<>("", getUIHttpHeaders(jsessionId));
//		return new RestTemplate().exchange(url, HttpMethod.GET, getReq, JsonNode.class).getBody();
		return null;
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

			JsonNode objects = list(clusterIdUrl, jsessionId);

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
	
	public JsonNode search(String name, String type, String jsessionId) {

		try {
			String clusterIdUrl = this.client.getUrl() + "/ui/search/quicksearch/?opId=0&query=" + name;

			JsonNode objects = list(clusterIdUrl, jsessionId);

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
