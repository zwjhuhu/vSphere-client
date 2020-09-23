/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kubesys.vsphere.impls.VirtualMachineImpl;
import com.github.kubesys.vsphere.impls.VirtualMachineNetworkImpl;
import com.github.kubesys.vsphere.impls.VirtualMachinePoolImpl;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public class VsphereClient {

	private final String session;

	private final OkHttpClient httpClient;
	
	private final String url;
	
	private final String username;
	
	private final String password;
	
	public static String VERSION;
	
	
	
	/*********************************************************************
	 * 
	 * 
	 *                     Init
	 * @throws Exception 
	 * 
	 * 
	 *******************************************************************/
	public VsphereClient(String ip, String username, String password) throws Exception {
		this(ip, -1, username, password);
	}

	public VsphereClient(String ip, int port, String username, String password) throws Exception {
		this(ip, port, username, password, "6.7");
	}
	
	public VsphereClient(String ip, int port, String username, String password, String version) throws Exception {
		this("https", ip, port, username, password, version);
	}
	
	public VsphereClient(String protocol, String ip, int port, String username, String password, String version) throws Exception {
		this.url = protocol + "://" + (port <= 0 ? ip : ip + ":" + port);
		this.username = username;
		this.password = password;
		VERSION = version;
		this.httpClient = createHttpClient();
		
		Request request = createPostRequest(this.url + 
				"/rest/com/vmware/cis/session", "text/plain", "");
		
		Response resp = httpClient.newCall(request).execute();
		this.session = new ObjectMapper().readTree(resp.body().byteStream()).get("value").asText();
	}


	/*******************************************************
	 * 
	 *
	 *               Create
	 * 
	 ********************************************************/
	
	protected Request createPostRequest(String url, String type, String body) {
		return new Request.Builder()
		  .url(url)
		  .addHeader("Authorization", "Basic " + getBase64Creds(username, password))
		  .method("POST", createPostBody(type, body))
		  .build();
	}
	
	@SuppressWarnings("deprecation")
	protected RequestBody createPostBody(String type, String obj) {
		MediaType mediaType = MediaType.parse(type);
		return RequestBody.create(mediaType, obj);
	}

	protected OkHttpClient createHttpClient() throws Exception {
		X509TrustManager initTrustManager = initTrustManager();
		return new OkHttpClient.Builder()
				.sslSocketFactory(initSslSocketFactory(
    							initTrustManager), initTrustManager)
				.hostnameVerifier(initHostnameVerifier())
				.followRedirects(false)
				.build();
	}

	/**
	 * @param trustManager                    trustManager
	 * @return                                SSLSocketFactory
	 * @throws NoSuchAlgorithmException       NoSuchAlgorithmException
	 * @throws KeyManagementException         KeyManagementException
	 */
	protected SSLSocketFactory initSslSocketFactory(X509TrustManager trustManager)
			throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{trustManager}, null);
		return sslContext.getSocketFactory();
	}
	
	/**
	 * @return                                X509TrustManager  
	 */
	protected X509TrustManager initTrustManager() {
		return new X509TrustManager() {

			@Override
		    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		    	// Do nothing
				if (chain == null) {
		    		throw new CertificateException("Client is not using tls");
		    	}
		    }

		    @Override
		    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		    	// Do nothing
		    	if (chain == null) {
		    		throw new CertificateException("Server is not using tls");
		    	}
		    }

		    @Override
		    public X509Certificate[] getAcceptedIssuers() {
		        return new X509Certificate[0];
		    }
		};
	}

	/**
	 * @return                                hostnameVerifier   
	 */
	protected HostnameVerifier initHostnameVerifier() {
		return new HostnameVerifier() {

			@Override
			public String toString() {
				return super.toString();
			}

			@Override
			public boolean verify(String hostname, SSLSession arg1) {
				return hostname != null;
			}
			
		};
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
	
	public static String getBase64Creds(String username, String password) {
		String authString = username + ":" + password;
		return new BASE64Encoder().encode(authString.getBytes());
	}
	

	/**
	 * 
	 * https://stackoverflow.com/questions/59647549/how-do-i-filter-using-a-partial-vm-name-string-in-vmware-vsphere-client-rest-a/61959622
	 * @throws Exception 
	 * 
	 */
	public JsonNode loginUrl() throws Exception {

		Request request = new Request.Builder()
				.url(this.url + "/ui/login")
				.addHeader("Authorization", "Basic " + getBase64Creds(username, password))
				.method("GET", null)
				.build();
				
		return new ObjectMapper().readTree(new ObjectMapper().writeValueAsBytes(
						httpClient.newCall(request).execute().headers()));
			
	}
	
	public String getKeyInHeader(String key, JsonNode json) {
		int size = json.size();
		for (int i = 0; i < size; i++) {
			JsonNode item = json.get(i);
			if (item.get("first").asText().equals(key)) {
				return item.get("second").asText();
			}
		}
		return null;
	}
//	
//	public JsonNode samlUrl(String url, String token) {
//
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//			headers.add("Authorization", "Basic " + token);
//			headers.add("Cookie", "VSPHERE-UI-JSESSIONID=D54653702ED4D25AAC213D7E2DA9EEC0");
//			
//			HttpEntity<String> request = new HttpEntity<>("CastleAuthorization=Basic%20" + token, headers);
//			
//			ResponseEntity<Object> samlResp = new RestTemplate()
//					.exchange(url, 
//					HttpMethod.POST,
//					request, Object.class);
//
//			return new ObjectMapper().readTree(
//					new ObjectMapper().writeValueAsBytes(samlResp));
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//
//		}
//		return null;
//	}

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
	
	public OkHttpClient getHttpClient() {
		return httpClient;
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
