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

	/***************************************************************
	 * 
	 *                  List
	 * 
	 ****************************************************************/
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
			return list(this.client.getUrl() + "/rest/vcenter/vm_template/library-items");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listResourcePools() throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/resource-pool");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// https://133.133.135.35/ui/events/?requestedPage=0
	public JsonNode listEvents(String jsessionId) throws Exception {
		try {
			return list(this.client.getUrl() + "/ui/events", jsessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/***************************************************************
	 * 
	 *                  Get
	 * 
	 ****************************************************************/
	
	public JsonNode getDataCenter(String name) throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/datacenter/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getDataStore(String name) throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/datastore/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getCluster(String name) throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/cluster/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode getVM(String name) throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/vm/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getResourcePool(String name) throws Exception {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/resource-pool/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getClusterInfo(String cluster, String jsessionId) {
		
		try {
			String clusterIdUrl = this.client.getUrl() + "/ui/search/quicksearch/?opId=0&query=" + cluster;
			
			JsonNode list = list(clusterIdUrl, jsessionId);
			String id = 
					
					list.get(0).get("results").get(0).get("id").asText();
			
			String clusterInfoUrl = this.client.getUrl() + "/ui/data/" + id + "?model=com.vmware.vsphere.client.cluster.model.ClusterSummaryData";
			return list(clusterInfoUrl, jsessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
