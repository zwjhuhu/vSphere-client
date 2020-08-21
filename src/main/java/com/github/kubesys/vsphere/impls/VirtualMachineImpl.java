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
}
