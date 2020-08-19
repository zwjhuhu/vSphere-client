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
public class VirtualMachinePoolImpl extends AbstractImpl  {

	public VirtualMachinePoolImpl(VsphereClient client) {
		super(client);
	}

	public JsonNode listDataCenters() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/datacenter");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listDataStores() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/datastore");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listFolders() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/folder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listNetworks() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/network");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listClusters() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/cluster");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listHosts() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/host");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode listVMs() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/vm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listVMTemplates() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/vm_template");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getClusterInfo(String cluster, String jsessionId) {
		
		try {
			String clusterIdUrl = this.client.getUrl() + "/ui/search/quicksearch/?opId=0&query=" + cluster;
			
			String id = ui(clusterIdUrl, jsessionId).get(0).get("results").get(0).get("id").asText();
			
			String clusterInfoUrl = this.client.getUrl() + "/ui/data/" + id + "?model=com.vmware.vsphere.client.cluster.model.ClusterSummaryData";
			return ui(clusterInfoUrl, jsessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
