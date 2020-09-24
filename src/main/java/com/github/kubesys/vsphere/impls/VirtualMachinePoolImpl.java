/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;

import okhttp3.OkHttpClient;

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
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/datacenter");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listDataStores() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/datastore");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listDataStores(String datacenterid) throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/datastore?filter.datacenters=" + datacenterid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listFolders() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/folder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listNetworks() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/network");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listClusters() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/cluster");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listClusters(String datacenterId) throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/cluster?filter.datacenters=" + datacenterId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listHosts() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/host");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode listVMs() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static String LIST_VM_TEMPLATE = "{\"constraintObjectId\":\"DATACENTER\",\"queryFilterId\":\"relatedItemsListFilterId\",\"filterParams\":[\"vmTemplatesForDatacenter\"],\"requestedProperties\":[\"id\",\"primaryIconId\",\"name\",\"labelIds\",\"provisionedSpace.@formatted\",\"config/guestFullName\",\"hwVersion.@formatted\",\"summary.config.memorySizeMB.@formatted\"],\"dataModels\":[\"VirtualMachine\"],\"take\":100,\"skip\":0,\"sort\":[{\"field\":\"name\",\"dir\":\"desc\"}],\"listViewId\":\"vsphere.core.template.list\",\"isLiveRefreshRequest\":false}";
	
	public JsonNode listVMTemplates(String datacenter, String cookie) throws Exception {
		try {
			String id = search(datacenter, "Datacenter", cookie)
									.get("id").asText();
			OkHttpClient newClient = client.createHttpClient(true);
			return postWithCookie(newClient, "https://133.133.135.35/ui/list/ex", 
								LIST_VM_TEMPLATE.replace("DATACENTER", id), cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode listResourcePools() throws Exception {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/resource-pool");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// https://133.133.135.35/ui/events/?requestedPage=0
	public JsonNode listEvents(int page, String cookie) throws Exception {
		try {
			return listWithCookie(this.client.getUrl() + "/ui/events/?requestedPage=" + page, cookie);
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
			return getWithoutCookie(this.client.getUrl() + "/rest/vcenter/datacenter/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode getDataStore(String name) throws Exception {
		try {
			return getWithoutCookie(this.client.getUrl() + "/rest/vcenter/datastore/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getCluster(String name) throws Exception {
		try {
			return getWithoutCookie(this.client.getUrl() + "/rest/vcenter/cluster/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode getVM(String name) throws Exception {
		try {
			return getWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getResourcePool(String name) throws Exception {
		try {
			return getWithoutCookie(this.client.getUrl() + "/rest/vcenter/resource-pool/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/******************************************************************************
	 * 
	 * 
	 *                          Info
	 * 
	 * 
	 ******************************************************************************/
	public JsonNode getClusterInfo(String cluster, String cookie) {
		try {
			String id = search(cluster, "Cluster", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.cluster.model.ClusterSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getDataCenterInfo(String datacenter, String cookie) {
		try {
			String id = search(datacenter, "Datacenter", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.datacenter.model.DatacenterSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getDataStoreInfo(String datastore, String cookie) {
		try {
			String id = search(datastore, "Datastore", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.h5.storage.model.DatastoreSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getResourcePoolInfo(String pool, String cookie) {
		try {
			String id = search(pool, "Resource Pool", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.rp.model.ResourcePoolSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
