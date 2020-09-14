/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kubesys.vsphere.VsphereClient;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public abstract class AbstractImpl  {
	
	/**
	 * client
	 */
	protected final VsphereClient client;

	public AbstractImpl(VsphereClient client) {
		super();
		this.client = client;
	}
	
	protected HttpHeaders getDefHttpHeaders() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		if (client.getSession() != null) {
			headers.add("Cookie", "vmware-api-session-id=" + client.getSession());
		}
		return headers;
	}
	
	
	protected HttpHeaders getUIHttpHeaders(String jsessionId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		if (client.getSession() != null) {
			headers.add("Cookie", "VSPHERE-USERNAME=" + client.getUsername() + ";VSPHERE-UI-JSESSIONID=" + jsessionId);
			headers.add("X-VSPHERE-UI-XSRF-TOKEN", "516494cd-ac4c-4588-9daa-4de88c9ec148");
		}
		return headers;
	}
	
	/**
	 * 
	 * https://stackoverflow.com/questions/59647549/how-do-i-filter-using-a-partial-vm-name-string-in-vmware-vsphere-client-rest-a/61959622
	 * 
	 */
	public String getJessionId()  {
		
		try {
		String loginUrl = client.getUrl() + "/ui/login";
		ResponseEntity<JsonNode> loginResp = new RestTemplate().exchange(
				loginUrl, HttpMethod.POST, new HttpEntity<>("", new HttpHeaders()), JsonNode.class);
		
		System.out.println(new ObjectMapper().writeValueAsString(loginResp));
		String samlUrl = loginResp.getHeaders().get("Location").get(0);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + VsphereClient.getBase64Creds(
				client.getUsername(), client.getPassword()));
		ResponseEntity<JsonNode> samlResp = new RestTemplate().exchange(
				samlUrl, HttpMethod.POST, new HttpEntity<>("", headers), JsonNode.class);
		System.out.println(new ObjectMapper().writeValueAsString(samlResp));
		
		
		
		String fullUrl = client.getUrl() + "/ui/saml/websso/sso";
		} catch (Exception ex) {
			
		}
		return null;
	}
	
	
	protected JsonNode list(String url) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getDefHttpHeaders());
		return new RestTemplate().exchange(url, 
				HttpMethod.GET, getReq, JsonNode.class).getBody();
	}
	
	protected JsonNode post(String url) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getDefHttpHeaders());
		return new RestTemplate().exchange(url, 
				HttpMethod.POST, getReq, JsonNode.class).getBody();
	}
	
	protected JsonNode remove(String url) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getDefHttpHeaders());
		return new RestTemplate().exchange(url, 
				HttpMethod.DELETE, getReq, JsonNode.class).getBody();
	}
	
	protected JsonNode patch(String url, String body) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>(body, getDefHttpHeaders());
		CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(
						new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
	                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		return restTemplate.exchange(url, 
				HttpMethod.PATCH, getReq, JsonNode.class).getBody();
	}
	
	protected JsonNode list(String url, String jsessionId) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getUIHttpHeaders(jsessionId));
		return new RestTemplate().exchange(url, 
				HttpMethod.GET, getReq, JsonNode.class).getBody();
	}
	
	protected JsonNode post(String url, String body, String jsessionId) throws Exception {
		HttpEntity<String> postReq = new HttpEntity<>(body, getUIHttpHeaders(jsessionId));
		return new RestTemplate().exchange(url, 
				HttpMethod.POST, postReq, JsonNode.class).getBody();
	}
	
	protected JsonNode get(String url, String jsessionId) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getUIHttpHeaders(jsessionId));
		System.out.println(url);
		return new RestTemplate().exchange(url, 
				HttpMethod.GET, getReq, JsonNode.class).getBody();
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
}
