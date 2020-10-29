package com.github.kubesys.vsphere.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.vsphere.VsphereClient;
import com.github.kubesys.vsphere.VsphereClientTest;

public class CreateVMFromTemplateTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		String cookie = "CastleSessionvsphere.local=_2d05aa0da587093a49c98dd3f1b82e1f; JSESSIONID=57F44F0ABC2A4D9CB1755EC117D3B60B8705; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_04877847fca7979a00e6d537db080174";
		String token = "b54655c2-7a78-4c8b-9162-1acae9423c34";
		VsphereClient vClient = getClient();
		JsonNode createFromTemplate = vClient.virtualMachines().createFromTemplate("henry20", "vm-238", "group-v3", 
				"datastore-18", "新建资源池1", "host-17", cookie, token);
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
