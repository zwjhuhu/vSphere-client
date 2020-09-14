/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

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
			return list(this.client.getUrl() + "/rest/vcenter/network");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode createL3(String dc, String uuid, String name) {
		return createL3(dc, uuid, name, getJessionId());
	}
	
	public JsonNode createL3(String dc, String uuid, String name, String jsessionId) {
		String fullUrl = this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.h5.network.dvs.create.model.DvsCreateWizardMutationSpec";
		try {
			return post(fullUrl, L3.replace("DCNAME", dc)
									.replace("UUID", uuid)
									.replace("NAME", name), jsessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode deleteL3(String name, String jsessionId) {
		String fullUrl = this.client.getUrl() + "/ui/data/properties/ID?properties=name";
		String id = searchRealname(name, "Distributed Switch", jsessionId);
		try {
			return get(fullUrl.replace("ID", id), jsessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
