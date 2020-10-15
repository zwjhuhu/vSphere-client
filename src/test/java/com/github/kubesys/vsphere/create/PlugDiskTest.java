package com.github.kubesys.vsphere.create;

import com.github.kubesys.vsphere.VsphereClientTest;

/**
 * Unit test for simple App.
 */
public class PlugDiskTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		//10GB硬盘，硬盘插入位置，比如当前虚拟机有1块盘（存放在0位置），则可以填写1
		System.out.println(getClient().virtualMachines().plugVMDisk("vm-21", "test", 10240000000L, 1)); 
	}
}

