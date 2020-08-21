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
			return list(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonNode createFromTemplate() {
		return null;
	}
	
	public JsonNode getImageInfo(String vm) {
		try {
			return list(this.client.getUrl() + "/rest/vcenter/vm/"+vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean start(String vm) {
		try {
			post(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/start");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean stop(String vm) {
		try {
			post(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/stop");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean suspend(String vm) {
		try {
			post(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/suspend");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean reset(String vm) {
		try {
			post(this.client.getUrl() + "/rest/vcenter/vm/" + vm + "/power/reset");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean reboot(String vm) {
		try {
			post(this.client.getUrl() + "/vcenter/vm/" + vm + "/guest/power?action=reboot");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean shutdown(String vm) {
		try {
			post(this.client.getUrl() + "/vcenter/vm/" + vm + "/guest/power?action=shutdown");
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
