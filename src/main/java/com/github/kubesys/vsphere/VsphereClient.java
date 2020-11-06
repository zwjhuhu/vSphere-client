/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
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
	
	private final String version;
	
	
	
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
		this.version = version;
		this.httpClient = createHttpClient(false);
		this.session = new ObjectMapper().readTree(
				simpleLogin(username, password).body().byteStream()).get("value").asText();
		try {
			virtualMachines().valid(virtualMachinePools().listNetworks());
		} catch (Exception ex) {
			throw new Exception("Invalid url, or username, or password");
		}
	}

	/*******************************************************
	 * 
	 *
	 *               Init
	 * 
	 ********************************************************/

	public OkHttpClient createHttpClient(boolean redirect) throws Exception {
		X509TrustManager initTrustManager = initTrustManager();
		return new OkHttpClient.Builder()
				.sslSocketFactory(initSslSocketFactory(
    							initTrustManager), initTrustManager)
				.hostnameVerifier(initHostnameVerifier())
				.followRedirects(redirect)
				.callTimeout(java.time.Duration.ZERO)
				.connectTimeout(java.time.Duration.ZERO)
				.readTimeout(java.time.Duration.ZERO)
				.writeTimeout(java.time.Duration.ZERO)
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
	
	/*******************************************************
	 * 
	 *
	 *               Login
	 * 
	 ********************************************************/
	
	@SuppressWarnings("deprecation")
	protected Response simpleLogin(String username, String password) throws IOException {
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body =  RequestBody.create(mediaType, "");
		
		Request request = new Request.Builder()
				  .url(this.url + "/rest/com/vmware/cis/session")
				  .addHeader("Authorization", "Basic " + getBase64Creds(username, password))
				  .method("POST", body)
				  .build();
		
		return httpClient.newCall(request).execute();
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
	
	@SuppressWarnings("deprecation")
	public Response saml2Url(String url, String cookie) throws Exception {

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "CastleAuthorization=Basic "
							+ getBase64Creds(username, password));
		
		Request request = new Request.Builder()
				.url(url)
				.addHeader("Cookie", cookie)
				.addHeader("Authorization", "Basic " + getBase64Creds(username, password))
				.method("POST", body)
				.build();
				
		return httpClient.newCall(request).execute();
			
	}
	
	@SuppressWarnings("deprecation")
	public Response webssoUrl(String cookie, String parameter) throws Exception {

		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		
		Request request = new Request.Builder()
				.url(this.url + "/ui/saml/websso/sso?SAMLResponse=" + URLEncoder.encode(parameter, "utf-8"))
				.addHeader("Cookie", cookie)
				.addHeader("Authorization", "Basic " + getBase64Creds(username, password))
				.method("POST", body)
				.build();
				
		return httpClient.newCall(request).execute();
			
	}
	
	public JsonNode uiUrl(String cookie) throws Exception {

		Request request = new Request.Builder()
				.url(this.url + "/ui/")
				.addHeader("Authorization", "Basic " + getBase64Creds(username, password))
				.addHeader("Cookie", cookie)
				.method("GET", null)
				.build();
				
		return new ObjectMapper().readTree(new ObjectMapper().writeValueAsBytes(
						httpClient.newCall(request).execute().headers()));
			
	}
	
	/*******************************************************
	 * 
	 *
	 *               Utils
	 * 
	 ********************************************************/
	public static String getBase64Creds(String username, String password) {
		String authString = username + ":" + password;
		return new BASE64Encoder().encode(authString.getBytes());
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
	
    public String getXSRFToken(String cookie) throws Exception {
    	JsonNode uiJson = uiUrl(cookie);
		String str = getKeyInHeader("Set-Cookie", uiJson);
		int stx = str.indexOf("=");
		int etx = str.lastIndexOf(";");
		return str.substring(stx + 1, etx);
    }

	public String getSAMLResponse(InputStream is) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		
		String value = sb.substring(0, sb.length() - 2).toString();
		int sIdx = value.indexOf("value=\"");
		int mIdx = value.indexOf("<input type=\"submit\"");
		int eIdx = value.substring(0, mIdx).lastIndexOf("\"");
		return value.substring(sIdx + 7, eIdx);
	}

	public String getCookie() throws Exception {
		StringBuilder sb = new StringBuilder();
		
		JsonNode jsonNode = loginUrl();
		String cookie1 = getKeyInHeader("Set-Cookie", jsonNode);
		Response resp = saml2Url(getKeyInHeader("Location", jsonNode), cookie1);
		
		String cookie2 = resp.header("Set-Cookie");
		sb.append(getRealCookie(cookie2)).append("; ");
		
		Response webssoUrl = webssoUrl(cookie2,
				getSAMLResponse(resp.body().byteStream()));
		JsonNode json = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(webssoUrl.headers()));
		
		int size = json.size();
		for (int i = 0; i < size; i++) {
			JsonNode item = json.get(i);
			if (item.get("first").asText().equals("Set-Cookie")) {
				sb.append(getRealCookie(item.get("second").asText())).append("; ");
			}
		}

		return sb.substring(0, sb.length() - 2).toString();
	}

	public String getRealCookie(String cookie) {
		int idx = cookie.indexOf(";");
		return cookie.substring(0, idx);
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

	public String getVersion() {
		return version;
	}
	
}
