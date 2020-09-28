package com.github.kubesys.vsphere.list;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.github.kubesys.vsphere.VsphereClientTest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Unit test for simple App.
 */
public class ListTemplatesTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.test=_4f0b3176fb9a61b0b4bb1e0eb70d39b7; VSPHERE-UI-JSESSIONID=D0160A26831033FD8FF5E8E58CDA7A65; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_71805559b45ef3cb84c5bf54bcfc8e13; VSPHERE-UI-XSRF-TOKEN=458d92d0-3d10-4042-aca2-6cc161a52a33";
		System.out.println(getClient().virtualMachinePools().listVMTemplates("Datacenter", cookie));
		
//		OkHttpClient client = getClient().getHttpClient();
//		Request request = new Request.Builder()
//		  .url("https://133.133.135.35/ui/list/ex")
//		  .method("GET", null)
//		  .addHeader("Cookie", "CastleSessionvsphere.test=_254b7612f5072343ebbed493eb4d012f; VSPHERE-UI-JSESSIONID=840C2825F931C4E5B022BB3839FD3B08; VSPHERE-USERNAME=Administrator%40VSPHERE.TEST; VSPHERE-CLIENT-SESSION-INDEX=_6fe34a6bfe9c92c7df2ee252d6f9c764; VSPHERE-UI-XSRF-TOKE=458d92d0-3d10-4042-aca2-6cc161a52a35")
//		  .addHeader("X-VSPHERE-UI-XSRF-TOKEN", "458d92d0-3d10-4042-aca2-6cc161a52a35")
//		  .addHeader("Authorization", "Basic YWRtaW5pc3RyYXRvckB2c3BoZXJlLnRlc3Q6T25jZWFzMjAyMCEyMzQ=")
//		  .addHeader("Content-Type", "application/json")
//		  .build();
//		Response response = client.newCall(request).execute();
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));
//		String line = null;
//		
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//		}
	}
}

