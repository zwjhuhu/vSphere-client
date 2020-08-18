/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
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
	
	protected HttpHeaders getHttpHeaders() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		if (client.getSession() != null) {
			headers.set("Cookie", "vmware-api-session-id= " + client.getSession());
		}
		return headers;
	}
	
	protected JsonNode list(String url) throws Exception {
		HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
		return new RestTemplate().exchange(url, 
				HttpMethod.GET, getReq, JsonNode.class).getBody();
	}
	
}
