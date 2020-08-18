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
	
	public JsonNode getClusterInfo() {
		String url = this.client.getUrl() + "/ui/data/urn:vmomi:ClusterComputeResource:domain-c16:9e4a98b3-189a-475b-b093-f5cda70cd2a5?model=com.vmware.vsphere.client.cluster.model.ClusterSummaryData";
		try {
			return list(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
