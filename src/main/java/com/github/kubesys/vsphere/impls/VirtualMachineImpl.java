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
	
	public JsonNode createFromTemplate(String name, String template, String folder, String datastore, String pool, String uuid, String jsessionId) {
		
		try {
			String JSON = CLONE.replace("VMNAME", name)
							.replace("TEMPLATENAME", template)
							.replace("FOLDERNAME", folder)
							.replace("POOLNAME", pool)
							.replaceAll("DATASTORENAME", datastore)
							.replaceAll("UUID", uuid);
			System.out.println(JSON);
			return post(this.client.getUrl() + "/ui/mutation/add?propertyObjectType=com.vmware.vsphere.client.vm.VmCloneSpec", JSON, jsessionId);
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
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/start");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean stop(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/stop");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean suspend(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/suspend");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean reset(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/reset");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean shutdown(String vm) {
		try {
			postWithoutCookie(this.client.getUrl() + "/vcenter/vm/" + vm + "/guest/power?action=shutdown");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String vm) {
		try {
			remove(this.client.getUrl() + "/rest/vcenter/vm/" + vm);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	static String CPU = "{\r\n" + 
			"	\"spec\": {\r\n" + 
			"		\"cores_per_socket\": 1,\r\n" + 
			"		\"count\": NUMBER,\r\n" + 
			"		\"hot_add_enabled\": false,\r\n" + 
			"		\"hot_remove_enabled\": false\r\n" + 
			"	}\r\n" + 
			"}";
	
	public boolean updateCPU(String vm, int num) {
		try {
			
			patch(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/hardware/cpu", CPU.replace("NUMBER", String.valueOf(num)));
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
	
	public boolean updateRAM(String vm, int size) {
		try {
			
			patch(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/hardware/memory", MEMORY.replace("SIZE", String.valueOf(size)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
