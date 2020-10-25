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
public class VirtualMachineImpl extends AbstractImpl  {

	public VirtualMachineImpl(VsphereClient client) {
		super(client);
	}
	
	public JsonNode getVMInfo(String vm) {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getVMInfo(String vm, String cookie) {
		try {
			String id = search(vm, "Virtual Machine", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.h5.vm.model.VmSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getVMTemplateInfo(String template, String cookie) {
		try {
			String id = search(template, "VM Template", cookie)
									.get("id").asText();
			return info(id, "com.vmware.vsphere.client.h5.vm.model.VmSummaryData", cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			"			\"host\": null,\r\n" + 
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
	
	public JsonNode createFromTemplate(String name, String templateid, String folderid, String datastoreid, String pool, String cookie, String token) {
		
		JsonNode poolJson = client.virtualMachinePools().getResourcePoolInfo(pool, cookie);
		
		String poolid = poolJson.get("provider").get("value").asText();;
		String uuid = poolJson.get("provider").get("serverGuid").asText();
		try {
			String JSON = CLONE.replace("VMNAME", name)
							.replace("TEMPLATENAME", templateid)
							.replace("FOLDERNAME", folderid)
							.replace("POOLNAME", poolid)
							.replaceAll("DATASTORENAME", datastoreid)
							.replaceAll("UUID", uuid);
			return postWithCookie(this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.vm.VmCloneSpec", JSON, cookie, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonNode createFromTemplate(String name, String templateid, String folderid, String datastoreid, String poolid, String uuid, String cookie, String token) {
		
		try {
			
			String JSON = CLONE.replace("VMNAME", name)
							.replace("TEMPLATENAME", templateid)
							.replace("FOLDERNAME", folderid)
							.replace("POOLNAME", poolid)
							.replaceAll("DATASTORENAME", datastoreid)
							.replaceAll("UUID", uuid);
			return postWithCookie(this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.vm.VmCloneSpec", JSON, cookie, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode clone(String name, String templateid, String folderid, String datastoreid, String pool, String cookie, String token) {
		return createFromTemplate(name, templateid, folderid, datastoreid, pool, cookie, token);
	}
	
	public static String TOIMAGE = "{\r\n" + 
			"	\"objectIds\": [\"ID\"],\r\n" + 
			"	\"propertyObjectType\": \"com.vmware.vsphere.client.vm.VmTemplateSpec\",\r\n" + 
			"	\"propertySpec\": \"{}\"\r\n" + 
			"}";
	
	public JsonNode createImageFromVM(String vm, String cookie, String token) {
		try {
			JsonNode searchUUID = searchUUID(vm, "Virtual Machine", cookie);
			String id = searchUUID.get(0).get("results").get(0).get("id").asText();
			return postWithCookie(this.client.getUrl() + "/ui/mutation/applyOnMultiEntity", 
					TOIMAGE.replace("ID", id), cookie, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode getImageInfo(String vm) {
		try {
			return listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean start(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
											+ vm + "/power/start", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean stop(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
											+ vm + "/power/stop", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean suspend(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
											+ vm + "/power/suspend", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean reset(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" 
											+ vm + "/power/reset", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean shutdown(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/vcenter/vm/" 
											+ vm + "/guest/power?action=shutdown", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String vmid) {
		try {
			removeWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid, "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// https://133.133.135.35/ui/events/?requestedPage=0
		public JsonNode listVMEvents(String vm, String cookie) throws Exception {
			try {
				String id = search(vm, "Virtual Machine", cookie)
						.get("id").asText();
				return listWithCookie(this.client.getUrl() + "/ui/events/?"
						+ "objectId=" + id, cookie);
//						+ "requestedPage=" + page, cookie);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	static String CPU = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"		\"cores_per_socket\": 1,\r\n" + 
			"		\"count\": NUMBER,\r\n" + 
			"		\"hot_add_enabled\": false,\r\n" + 
			"		\"hot_remove_enabled\": false\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean updateCPU(String vmid, int num) {
		try {
			
			patchWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/cpu", CPU.replace("NUMBER", String.valueOf(num)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	static String MEMORY = "{\r\n" + 
			"    \"spec\": {\r\n" + 
			"        \"hot_add_enabled\": false,\r\n" + 
			"        \"size_MiB\": SIZE\r\n" + 
			"    }\r\n" + 
			"}";
	
	public boolean updateRAM(String vmid, int size) {
		try {
			
			patchWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/memory", MEMORY.replace("SIZE", String.valueOf(size)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/disk",
					CREATE_DISK.replace("NAME", name).replace("SIZE", String.valueOf(size)).replace("UNIT", String.valueOf(unit)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/ethernet",
														CREATE_STD_NIC.replace("NAME", name));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean unplugVMDisk(String vmid, String diskId) throws Exception {
		try {
			listWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/disk/" + diskId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean unplugVMNIC(String vmid, String nicId) throws Exception {
		try {
			deleteWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vmid + "/hardware/ethernet/" + nicId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
