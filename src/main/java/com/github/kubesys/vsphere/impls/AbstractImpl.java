/**
 * Copyright (2020, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.vsphere.impls;

import com.github.kubesys.vsphere.vSphereClient;

/**
 * wuheng@otcaix.iscas.ac.cn
 *
 */
public abstract class AbstractImpl  {
	
	/**
	 * client
	 */
	protected final vSphereClient client;

	public AbstractImpl(vSphereClient client) {
		super();
		this.client = client;
	}
	
}
