/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kubesys.vsphere.impls.VirtualMachineImpl;
import com.github.kubesys.vsphere.impls.VirtualMachineNetworkImpl;
import com.github.kubesys.vsphere.impls.VirtualMachinePoolImpl;

import sun.misc.BASE64Encoder;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public class VsphereClient {

	private final String session;

	private final String url;
	
	private final String username;
	
	private final String password;
	
	public static String version;
	
	/*********************************************************************
	 * 
	 * 
	 *                     Init
	 * 
	 * 
	 *******************************************************************/
	public VsphereClient(String ip, String username, String password) {
		this(ip, -1, username, password);
	}

	public VsphereClient(String ip, int port, String username, String password) {
		this(ip, port, username, password, "6.7");
	}
	
	public VsphereClient(String ip, int port, String username, String password, String version) {
		disableSslVerification();
		this.url = "https://" + (port <= 0 ? ip : ip + ":" + port);
		this.username = username;
		this.password = password;
		ResponseEntity<Session> responseEntity = new RestTemplate().exchange(
					getFullUrl(), HttpMethod.POST, getHttpEntity(username, password), Session.class);
		this.session = responseEntity.getBody().getValue();
		VsphereClient.version = version;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class Session {
		@JsonProperty("value")
		private String value;
		

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	private String getFullUrl() {
		return this.url + "/rest/com/vmware/cis/session";
	}

	public static String getBase64Creds(String username, String password) {
		String authString = username + ":" + password;
		return new BASE64Encoder().encode(authString.getBytes());
	}
	
	public static HttpHeaders getHeaders(String base64Creds) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}

	public static HttpEntity<String> getHttpEntity(String username, String password) {
		return new HttpEntity<String>(getHeaders(
				getBase64Creds(username, password)));
	}
	
	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@SuppressWarnings("unused")
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				@SuppressWarnings("unused")
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {

				}

			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * https://stackoverflow.com/questions/59647549/how-do-i-filter-using-a-partial-vm-name-string-in-vmware-vsphere-client-rest-a/61959622
	 * 
	 */
	public String getUIJessionId() {

		try {
			String loginUrl = getUrl() + "/ui/login";
			ResponseEntity<JsonNode> loginResp = new RestTemplate().exchange(loginUrl, HttpMethod.POST ,
					new HttpEntity<>("", new HttpHeaders()), JsonNode.class);

			System.out.println(new ObjectMapper().writeValueAsString(loginResp));
			
			
			String samlUrl = loginResp.getHeaders().get("Location").get(0);
			
			String ssoUrl = getUrl() + "/ui/saml/websso/sso";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", "Basic " + VsphereClient.getBase64Creds(getUsername(), getPassword()));
			ResponseEntity<JsonNode> samlResp = new RestTemplate().exchange(samlUrl + "&CastleAuthorization=Basic%20"+ VsphereClient.getBase64Creds(getUsername(), getPassword()), 
					HttpMethod.GET, new HttpEntity<>("", headers), JsonNode.class);
			
			System.out.println(new ObjectMapper().writeValueAsString(samlResp));
//
//			String fullUrl = client.getUrl() + "/ui/saml/websso/sso";
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}
	
	public JsonNode loginUrl() {

		try {
			String loginUrl = getUrl() + "/ui/login";
			ResponseEntity<JsonNode> loginResp = new RestTemplate()
					.exchange(loginUrl, HttpMethod.POST,
					new HttpEntity<>("", new HttpHeaders()), JsonNode.class);

			return new ObjectMapper().readTree(
					new ObjectMapper().writeValueAsBytes(loginResp));
			
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}
	
	public JsonNode samlUrl(String url, String token) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("ContentType", "application/x-www-form-urlencoded");
//			headers.add("Authorization", "Basic " + token);
//			headers.add("KeepAlive ", "true");
//			headers.add("AllowAutoRedirect  ", "false");
			
			ObjectNode node = new ObjectMapper().createObjectNode();
			node.put("CastleAuthorization", "Basic%20" + token);
			
			HttpEntity<ObjectNode> request = new HttpEntity<>(node, headers);
			
			ResponseEntity<JsonNode> samlResp = new RestTemplate().exchange(url, 
					HttpMethod.POST,
					request, JsonNode.class);

			return new ObjectMapper().readTree(
					new ObjectMapper().writeValueAsBytes(samlResp));
			
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	/*********************************************************************
	 * 
	 * 
	 *                     Core
	 * 
	 * 
	 *******************************************************************/
	
	public VirtualMachinePoolImpl virtualMachinePools() {
		return new VirtualMachinePoolImpl(this);
	}
	
	public VirtualMachineNetworkImpl virtualMachineNetworks() {
		return new VirtualMachineNetworkImpl(this);
	}
	
	public VirtualMachineImpl virtualMachines() {
		return new VirtualMachineImpl(this);
	}
	
	
	/*********************************************************************
	 * 
	 * 
	 *                     Getter and Setter
	 * 
	 * 
	 *******************************************************************/
	
	
	
	public String getSession() {
		return session;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
