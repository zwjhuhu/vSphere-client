/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kubesys.vsphere.VsphereClient;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public class VirtualMachineImpl extends AbstractImpl  {

	public VirtualMachineImpl(VsphereClient client) {
		super(client);
	}
	
	public JsonNode getVMInfo(String vm) throws Exception {
		return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
	}
	
	public JsonNode getVMInfo(String vm, String cookie) throws Exception {
		String id = search(vm, "Virtual Machine", cookie)
								.get("id").asText();
		return info(id, "com.vmware.vsphere.client.h5.vm.model.VmSummaryData", cookie);
	}
	
	public JsonNode getVMTemplateInfo(String vm, String cookie) throws Exception {
		String id = search(vm, "VM Template", cookie)
								.get("id").asText();
		return info(id, "com.vmware.vsphere.client.h5.vm.model.VmSummaryData", cookie);
	}
	
	public JsonNode getVMTemplateHardwareInfo(String vm, String cookie) throws Exception {
		String id = search(vm, "VM Template", cookie)
								.get("id").asText();
		return detail(id, "com.vmware.vsphere.client.vm.model.HardwareViewData", cookie);
	}
	
	public JsonNode getVMHardwareInfo(String vm, String cookie) throws Exception {
		String id = search(vm, "Virtual Machine", cookie)
								.get("id").asText();
		return detail(id, "com.vmware.vsphere.client.vm.model.HardwareViewData", cookie);
	}
	
	
	static String CLONE = "{\r\n" + 
			"	\"vm\": {\r\n" + 
			"		\"type\": \"VirtualMachine\",\r\n" + 
			"		\"value\": \"TEMPLATENAME\",\r\n" + 
			"		\"serverGuid\": \"UUID\",\r\n" + 
			"		\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"	},\r\n" + 
			"	\"name\": \"VMNAME\",\r\n" + 
			"	\"folder\": {\r\n" + 
			"		\"type\": \"Folder\",\r\n" + 
			"		\"value\": \"FOLDERNAME\",\r\n" + 
			"		\"serverGuid\": \"UUID\",\r\n" + 
			"		\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"	},\r\n" + 
			"	\"cloneSpec\": {\r\n" + 
			"		\"_type\": \"com.vmware.vim.binding.vim.vm.CloneSpec\",\r\n" + 
			"		\"location\": {\r\n" + 
			"			\"_type\": \"com.vmware.vim.binding.vim.vm.RelocateSpec\",\r\n" + 
			"       \"host\": {\r\n" + 
			"			\"type\": \"HostSystem\",\r\n" + 
			"			\"value\": \"HOSTID\",\r\n" + 
			"			\"serverGuid\": \"UUID\",\r\n" + 
			"			\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"		}," +
			"			\"pool\": {\r\n" + 
			"				\"type\": \"ResourcePool\",\r\n" + 
			"				\"value\": \"POOLNAME\",\r\n" + 
			"				\"serverGuid\": \"UUID\",\r\n" + 
			"				\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"			},\r\n" + 
			"			\"datastore\": {\r\n" + 
			"				\"serverGuid\": \"UUID\",\r\n" + 
			"				\"type\": \"Datastore\",\r\n" + 
			"				\"value\": \"DATASTORENAME\"\r\n" + 
			"			},\r\n" + 
			"			\"profile\": [{\r\n" + 
			"				\"_type\": \"com.vmware.vim.binding.vim.vm.DefaultProfileSpec\"\r\n" + 
			"			}],\r\n" + 
			"			\"deviceChange\": [],\r\n" + 
			"			\"disk\": [{\r\n" + 
			"				\"_type\": \"com.vmware.vim.binding.vim.vm.RelocateSpec$DiskLocator\",\r\n" + 
			"				\"datastore\": {\r\n" + 
			"					\"serverGuid\": \"UUID\",\r\n" + 
			"					\"type\": \"Datastore\",\r\n" + 
			"					\"value\": \"DATASTORENAME\"\r\n" + 
			"				},\r\n" + 
			"				\"diskBackingInfo\": null,\r\n" + 
			"				\"diskId\": 2000,\r\n" + 
			"				\"profile\": [{\r\n" + 
			"					\"_type\": \"com.vmware.vim.binding.vim.vm.DefaultProfileSpec\"\r\n" + 
			"				}]\r\n" + 
			"			}]\r\n" + 
			"		},\r\n" + 
			"		\"template\": false,\r\n" + 
			"		\"powerOn\": false\r\n" + 
			"	}\r\n" + 
			"}";
	
	public JsonNode createFromTemplate(String name, String templateid, String serverGuid, String datastoreid, String folderid, String poolid, String hostid, String cookie, String token) throws Exception {
		
			ObjectNode node = new ObjectMapper().createObjectNode();
			
			{
				ObjectNode vm = new ObjectMapper().createObjectNode();
				vm.put("type", "VirtualMachine");
				vm.put("value", templateid);
				vm.put("serverGuid", serverGuid);
				vm.put("_type", "com.vmware.vim.binding.vmodl.ManagedObjectReference");
				node.set("vm", vm);
			}
			
			node.put("name", name);
			
			{
				ObjectNode folder = new ObjectMapper().createObjectNode();
				folder.put("type", "Folder");
				folder.put("value", folderid);
				folder.put("serverGuid", serverGuid);
				folder.put("_type", "com.vmware.vim.binding.vmodl.ManagedObjectReference");
				node.set("folder", folder);
			}
			
			{
				ObjectNode clone = new ObjectMapper().createObjectNode();
				clone.put("_type", "com.vmware.vim.binding.vim.vm.CloneSpec");
				
				{
					ObjectNode loc = new ObjectMapper().createObjectNode();
					loc.put("_type", "com.vmware.vim.binding.vim.vm.RelocateSpec");
					
					{
						ObjectNode host = null;
						if (hostid != null) {
							host = new ObjectMapper().createObjectNode();
							host.put("type", "HostSystem");
							host.put("value", hostid);
							host.put("serverGuid", serverGuid);
							host.put("_type", "com.vmware.vim.binding.vmodl.ManagedObjectReference");
						} 
						loc.set("host", host);
					}
					
					{
						ObjectNode pool = new ObjectMapper().createObjectNode();;
						pool.put("type", "ResourcePool");
						pool.put("value", poolid);
						pool.put("serverGuid", serverGuid);
						pool.put("_type", "com.vmware.vim.binding.vmodl.ManagedObjectReference");
						loc.set("pool", pool);
					}
					
					{
						
						ObjectNode dsnode = new ObjectMapper().createObjectNode();
						dsnode.put("serverGuid", serverGuid);
						dsnode.put("type", "Datastore");
						dsnode.put("value", datastoreid);
						loc.set("datastore", dsnode);
					}
					
					{
						
						ArrayNode list = new ObjectMapper().createArrayNode();
						
						ObjectNode item = new ObjectMapper().createObjectNode();
						item.put("_type", "com.vmware.vim.binding.vim.vm.DefaultProfileSpec");
						list.add(item);
						
						loc.set("profile", list);
					}
					
					{
						
						ArrayNode list = new ObjectMapper().createArrayNode();
						loc.set("deviceChange", list);
					}
					
					
					{
						ArrayNode list = new ObjectMapper().createArrayNode();
						
						ObjectNode item = new ObjectMapper().createObjectNode();
						item.put("_type", "com.vmware.vim.binding.vim.vm.RelocateSpec$DiskLocator");
						
						{
							
							ObjectNode dds = new ObjectMapper().createObjectNode();
							dds.put("type", "Datastore");
							dds.put("value", datastoreid);
							dds.put("serverGuid", serverGuid);
							item.set("datastore", dds);
						}
						
						item.set("diskBackingInfo", null);
						item.put("diskId", 2000);
						
						{
							ArrayNode array = new ObjectMapper().createArrayNode();
							
							ObjectNode aitem = new ObjectMapper().createObjectNode();
							aitem.put("_type", "com.vmware.vim.binding.vim.vm.DefaultProfileSpec");
							array.add(item);
							
							aitem.set("profile", array);
						}
						
						list.add(item);
						
						loc.set("disk", list);
					}
					clone.set("location", loc);
					
				}
				
				clone.put("template", false);
				clone.put("powerOn", false);
				node.set("cloneSpec", clone);
			}
			return postWithCookie(this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.vm.VmCloneSpec", 
								node.toString(), cookie, token);
	}
	
	
	public JsonNode selectResource(String datacenterId, String cookie) throws Exception {
		String url = this.client.getUrl() + "/ui/tree/children?nodeTypeId=RefAsRoot&objRef=" + datacenterId + "&treeId=DcHostsAndClustersTree";
		return listWithCookie(url, cookie);
	}
	
	public JsonNode selectResource(String typeId, String uuid, String cookie) throws Exception {
		String url = this.client.getUrl() + "/ui/tree/children?nodeTypeId=" + typeId + "&objRef=" + uuid + "&treeId=DcHostsAndClustersTree";
		return listWithCookie(url, cookie);
	}
	
	public JsonNode getTask(String taskid, String cookie) {
		
		try {
			String url = this.client.getUrl() + "/ui/tasks/?pageSize=100&requestedPage=0&requestingPreviousPage=true";
			JsonNode results = listWithCookie(url, cookie);
			for (int i = 0; i < results.size(); i++) {
				JsonNode res = results.get(i);
				if (res.get("key").asText().equals(taskid)) {
					return res;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String TOIMAGE = "{\r\n" + 
			"	\"objectIds\": [\"ID\"],\r\n" + 
			"	\"propertyObjectType\": \"com.vmware.vsphere.client.vm.VmTemplateSpec\",\r\n" + 
			"	\"propertySpec\": \"{}\"\r\n" + 
			"}";
	
	public JsonNode createImageFromVM(String vm, String cookie, String token) throws Exception {
		JsonNode searchUUID = searchUUID(vm, "Virtual Machine", cookie);
		String id = searchUUID.get(0).get("results").get(0).get("id").asText();
		return postWithCookie(this.client.getUrl() + "/ui/mutation/applyOnMultiEntity", 
				TOIMAGE.replace("ID", id), cookie, token);
	}
	
	public static String MIGRATE = "{\r\n" + 
			"	\"relocateSpec\": {\r\n" + 
			"		\"_type\": \"com.vmware.vim.binding.vim.vm.RelocateSpec\",\r\n" + 
			"		\"pool\": {\r\n" + 
			"			\"type\": \"ResourcePool\",\r\n" + 
			"			\"value\": \"POOLID\",\r\n" + 
			"			\"serverGuid\": \"SERVERID\",\r\n" + 
			"			\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"		},\r\n" + 
			"		\"host\": {\r\n" + 
			"			\"type\": \"HostSystem\",\r\n" + 
			"			\"value\": \"HOSTID\",\r\n" + 
			"			\"serverGuid\": \"SERVERID\",\r\n" + 
			"			\"_type\": \"com.vmware.vim.binding.vmodl.ManagedObjectReference\"\r\n" + 
			"		},\r\n" + 
			"		\"deviceChange\": []\r\n" + 
			"	},\r\n" + 
			"	\"priority\": \"highPriority\"\r\n" + 
			"}";
	
	public JsonNode migrateTo(String vm, String targetPoolId, String targetServerId, String targetHostId, String cookie, String token) throws Exception {
		JsonNode searchUUID = searchUUID(vm, "Virtual Machine", cookie);
		String id = searchUUID.get(0).get("results").get(0).get("id").asText().replace(":", "%3A");
		String url = this.client.getUrl() + "/ui/mutation/apply/" + id + "?propertyObjectType=com.vmware.vsphere.client.vm.migration.LocationSpec";
		return postWithCookie(url, MIGRATE.replace("POOLID", targetPoolId)
								.replace("HOSTID", targetHostId)
								.replaceAll("SERVERID", targetServerId), cookie, token);
	}
	
	public JsonNode getVMProperties(String vm, String cookie) throws Exception {
		JsonNode searchUUID = searchUUID(vm, "Virtual Machine", cookie);
		String id = searchUUID.get(0).get("results").get(0).get("id").asText();
		String url = this.client.getUrl() + "/ui/data/properties/" + id + "?properties=vmConfigContext";
		return listWithCookie(url, cookie);
	}
	
	public JsonNode getImageInfo(String vm) throws Exception {
		return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
	}
	
	public boolean start(String vm) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
										+ vm + "/power/start", "");
		return true;
	}
	
	public boolean stop(String vm) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
										+ vm + "/power/stop", "");
		return true;
	}
	
	public boolean suspend(String vm) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
										+ vm + "/power/suspend", "");
		return true;
	}
	
	public boolean reset(String vm) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
										+ vm + "/power/reset", "");
		return true;
	}
	
	public boolean shutdown(String vm) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/vcenter/vm/" 
										+ vm + "/guest/power?action=shutdown", "");
		return true;
	}
	
	public boolean delete(String vmid) throws Exception {
		removeWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid, "");
		return true;
	}
	
	// https://133.133.135.35/ui/events/?requestedPage=0
	public JsonNode listVMEvents(String vm, String cookie) throws Exception {
			String id = search(vm, "Virtual Machine", cookie)
					.get("id").asText();
			return listWithCookie(this.client.getUrl() + "/ui/events/?"
					+ "objectId=" + id, cookie);
	}
	
	static String CPU = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"		\"cores_per_socket\": 1,\r\n" + 
			"		\"count\": NUMBER,\r\n" + 
			"		\"hot_add_enabled\": false,\r\n" + 
			"		\"hot_remove_enabled\": false\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean updateCPU(String vmid, int num) throws Exception {
		patchWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/cpu", CPU.replace("NUMBER", String.valueOf(num)));
		return true;
	}
	
	static String MEMORY = "{\r\n" + 
			"    \"spec\": {\r\n" + 
			"        \"hot_add_enabled\": false,\r\n" + 
			"        \"size_MiB\": SIZE\r\n" + 
			"    }\r\n" + 
			"}";
	
	public boolean updateRAM(String vmid, int size) throws Exception {
		patchWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/memory", MEMORY.replace("SIZE", String.valueOf(size)));
		return true;
	}
	
	/******************************************************************************
	 * 
	 * 
	 *                          Delete
	 * 
	 * 
	 ******************************************************************************/
	
	public static String CREATE_DISK = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"		\"new_vmdk\": {\r\n" + 
			"			\"capacity\": SIZE,\r\n" + 
			"			\"name\": \"NAME\"\r\n" + 
			"		},\r\n" + 
			"		\"scsi\": {\r\n" + 
			"			\"bus\": 0,\r\n" + 
			"			\"unit\": UNIT\r\n" + 
			"		},\r\n" + 
			"		\"type\": \"SCSI\"\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean plugVMDisk(String vmid, String name, long size, int unit) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/disk",
				CREATE_DISK.replace("NAME", name).replace("SIZE", String.valueOf(size)).replace("UNIT", String.valueOf(unit)));
		return true;
	}
	
	public static String CREATE_STD_NIC = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"        \"backing\": {\r\n" + 
			"            \"network\": \"NAME\",\r\n" + 
			"            \"type\": \"STANDARD_PORTGROUP\"\r\n" + 
			"        },\r\n" + 
			"		\"allow_guest_control\": true,\r\n" + 
			"		\"mac_type\": \"ASSIGNED\",\r\n" + 
			"		\"start_connected\": true,\r\n" + 
			"		\"type\": \"VMXNET3\",\r\n" + 
			"		\"upt_compatibility_enabled\": true,\r\n" + 
			"		\"wake_on_lan_enabled\": true\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean plugVM_STD_NIC(String vmid, String name) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/ethernet",
													CREATE_STD_NIC.replace("NAME", name));
		return true;
	}
	
	public static String CREATE_DVS_NIC = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"        \"backing\": {\r\n" + 
			"            \"network\": \"NAME\",\r\n" + 
			"            \"type\": \"DISTRIBUTED_PORTGROUP\"\r\n" + 
			"        },\r\n" + 
			"		\"allow_guest_control\": true,\r\n" + 
			"		\"mac_type\": \"ASSIGNED\",\r\n" + 
			"		\"start_connected\": true,\r\n" + 
			"		\"type\": \"VMXNET3\",\r\n" + 
			"		\"upt_compatibility_enabled\": true,\r\n" + 
			"		\"wake_on_lan_enabled\": true\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean plugVM_DVS_NIC(String vmid, String name) throws Exception {
		postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/ethernet",
						CREATE_DVS_NIC.replace("NAME", name));
		return true;
	}
	
	public boolean unplugVMDisk(String vmid, String diskId) throws Exception {
		listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/disk/" + diskId);
		return true;
	}
	
	public boolean unplugVMNIC(String vmid, String nicId) throws Exception {
		deleteWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/ethernet/" + nicId);
		return true;
	}
	
	public static String DVSID = "{\r\n" + 
			"	\"constraintObjectId\": \"DVSID\",\r\n" + 
			"	\"queryFilterId\": \"relatedItemsListFilterId\",\r\n" + 
			"	\"filterParams\": [\"hostsForVMWDVS\"],\r\n" + 
			"	\"requestedProperties\": [\"id\", \"primaryIconId\", \"name\", \"labelIds\", \"stateLabel\", \"summary.overallStatus\", \"cluster\", \"hostClusterName\", \"cpuUsage\", \"memoryUsage\", \"dasHostState.@formatted\", \"runtime.dasHostState.state\", \"summary.quickStats.uptime.@formatted\"],\r\n" + 
			"	\"dataModels\": [\"HostSystem\"],\r\n" + 
			"	\"take\": 100,\r\n" + 
			"	\"skip\": 0,\r\n" + 
			"	\"sort\": [{\r\n" + 
			"		\"field\": \"name\",\r\n" + 
			"		\"dir\": \"asc\"\r\n" + 
			"	}],\r\n" + 
			"	\"listViewId\": \"vsphere.core.host.list\",\r\n" + 
			"	\"isLiveRefreshRequest\": false\r\n" + 
			"}";
	
	
	public boolean validDVS(String dvsid, String cookie) {
		try {
			String url = this.client.getUrl() + "/ui/list/ex/";
			JsonNode postWithCookie = postWithCookie(url, DVSID.replace("DVSID", dvsid), cookie, null);
			return postWithCookie.get("totalResultCount").asInt() != 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String DVSPortID = "{\r\n" + 
			"	\"constraintObjectId\": \"DVSPORTID\",\r\n" + 
			"	\"queryFilterId\": \"relatedItemsListFilterId\",\r\n" + 
			"	\"filterParams\": [\"hostsForDVPG\"],\r\n" + 
			"	\"requestedProperties\": [\"id\", \"primaryIconId\", \"name\", \"labelIds\", \"stateLabel\", \"summary.overallStatus\", \"cluster\", \"hostClusterName\", \"cpuUsage\", \"memoryUsage\", \"dasHostState.@formatted\", \"runtime.dasHostState.state\", \"summary.quickStats.uptime.@formatted\"],\r\n" + 
			"	\"dataModels\": [\"HostSystem\"],\r\n" + 
			"	\"take\": 100,\r\n" + 
			"	\"skip\": 0,\r\n" + 
			"	\"sort\": [{\r\n" + 
			"		\"field\": \"name\",\r\n" + 
			"		\"dir\": \"asc\"\r\n" + 
			"	}],\r\n" + 
			"	\"listViewId\": \"vsphere.core.host.list\",\r\n" + 
			"	\"isLiveRefreshRequest\": false\r\n" + 
			"}";
	
	public boolean validDVSPort(String dvsportid, String cookie) {
		try {
			String url = this.client.getUrl() + "/ui/list/ex/";
			JsonNode postWithCookie = postWithCookie(url, DVSPortID.replace("DVSPORTID", dvsportid), cookie, null);
			return postWithCookie.get("totalResultCount").asInt() != 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public JsonNode vmDetail(String vm, String cookie) throws Exception {
		String id = search(vm, "Virtual Machine", cookie)
				.get("id").asText();
		String url = this.client.getUrl() + "/ui/data/" + id + "?model=com.vmware.vsphere.client.h5.vm.model.VmSummaryData";
		return listWithCookie(url, cookie);
	}
}
