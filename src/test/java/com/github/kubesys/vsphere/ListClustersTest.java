package com.github.kubesys.vsphere;

/**
 * Unit test for simple App.
 */
public class ListClustersTest extends VsphereClientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().virtualMachinePools().listClusters());
	}
}

