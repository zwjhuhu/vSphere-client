package com.github.kubesys.vsphere.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

public class CreateVMFromTemplateTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		VsphereClient vClient = getClient();
		String cookie = vClient.getCookie();
		String token = vClient.getXSRFToken(cookie);
//		JsonNode createFromTemplate = vClient.virtualMachines().createFromTemplate("henry20", "vm-70", "9e4a98b3-189a-475b-b093-f5cda70cd2a5", "datastore-10", "group-v3", 
//				"resgroup-17", "host-9", cookie, token);
		JsonNode createFromTemplate = vClient.virtualMachines().createFromTemplate("henry22", "vm-70", "9e4a98b3-189a-475b-b093-f5cda70cd2a5", "datastore-10", "group-v3", 
				"resgroup-26", null, cookie, token);
		System.out.println(createFromTemplate.toPrettyString());
		String taskId = createFromTemplate.get("task").get("value").asText();

		check(vClient, taskId, cookie);
		
	}

	private static void check(VsphereClient vClient, String taskId, String cookie) throws Exception {
		JsonNode taskInfo = vClient.virtualMachines().getTask(taskId, cookie);
		String state = taskInfo.get("state").asText();
		
		if (state.equals("SUCESS")) {
			// resources
			JsonNode json = client.virtualMachines().search("henry20", "Virtual Machine", cookie);
			String asText = json.get("id").asText();
			int eid = asText.lastIndexOf(":");
			String vmid = asText.substring("urn:vmomi:VirtualMachine:".length(), eid);
			vClient.virtualMachines().updateCPU(vmid, 4);
			vClient.virtualMachines().updateRAM(vmid, 100234);
			
			// network
			
			vClient.virtualMachines().unplugVMNIC(vmid, "2000");
			// STD是标准网络，DVS是分布式网络，根据需要挂载
//			vClient.virtualMachines().plugVM_STD_NIC("vm-43", "network-11");
			vClient.virtualMachines().plugVM_DVS_NIC(vmid, "network-11");
		} else if (state.equals("ERROR")) {
			JsonNode error = taskInfo.get("error");
			JsonNode mesg = error.get("localizedMessage");
			System.out.println(mesg.asText());
		} else {
			// wait and retry
			Thread.sleep(2000);
			check(vClient, taskId, cookie);
		}
	}
}
