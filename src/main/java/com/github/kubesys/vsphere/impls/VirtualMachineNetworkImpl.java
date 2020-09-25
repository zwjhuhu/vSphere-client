/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public class VirtualMachineNetworkImpl extends AbstractImpl  {

	public VirtualMachineNetworkImpl(VsphereClient client) {
		super(client);
	}

	static String L3 = "{\r\n" + 
			"	\"parentEntityMor\": {\r\n" + 
			"		\"type\": \"Datacenter\",\r\n" + 
			"		\"value\": \"DCNAME\",\r\n" + 
			"		\"serverGuid\": \"UUID\",\r\n" + 
			"		\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"	},\r\n" + 
			"	\"uniqueDvsName\": \"NAME\",\r\n" + 
			"	\"selectedVersion\": \"6.5.0\",\r\n" + 
			"	\"numberOfUplinks\": 4,\r\n" + 
			"	\"niocEnabled\": true,\r\n" + 
			"	\"createDefaultPortgroup\": true,\r\n" + 
			"	\"defaultPortgroupName\": \"DPortGroup 1\"\r\n" + 
			"}";
	
	public JsonNode listNetworks() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/network");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode createDvs(String datacenter, String name, String cookie) {
		
		JsonNode json = client.virtualMachinePools().getDataCenterInfo(datacenter, cookie);
		
		String dc = json.get("provider").get("value").asText();
		String uuid = json.get("provider").get("serverGuid").asText();
		
		String fullUrl = this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.h5.network.dvs.create.model.DvsCreateWizardMutationSpec";
		try {
			return postWithCookie(fullUrl, L3.replace("DCNAME", dc)
									.replace("UUID", uuid)
									.replace("NAME", name), cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode deleteDvs(String name, String cookie) throws Exception {
		String id = searchRealname(name, "Distributed Switch", cookie);
		String fullUrl = this.client.getUrl() + "/ui/mutation/remove/" + URLEncoder.encode(id, "utf-8");
		try {
			return postWithCookie(fullUrl.replace("ID", id), "", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
