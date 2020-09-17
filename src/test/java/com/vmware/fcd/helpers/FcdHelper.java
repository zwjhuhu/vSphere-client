/*
 * *****************************************************
 * Copyright VMware, Inc. 2016.  All Rights Reserved.
 * *****************************************************
 *
 * DISCLAIMER. THIS PROGRAM IS PROVIDED TO YOU "AS IS" WITHOUT
 * WARRANTIES OR CONDITIONS # OF ANY KIND, WHETHER ORAL OR WRITTEN,
 * EXPRESS OR IMPLIED. THE AUTHOR SPECIFICALLY # DISCLAIMS ANY IMPLIED
 * WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY # QUALITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.vmware.fcd.helpers;

import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.BaseConfigInfoBackingInfo;
import com.vmware.vim25.BaseConfigInfoFileBackingInfo;
import com.vmware.vim25.DeviceGroupId;
import com.vmware.vim25.FaultDomainId;
import com.vmware.vim25.ID;
import com.vmware.vim25.ReplicationGroupId;
import com.vmware.vim25.ReplicationSpec;
import com.vmware.vim25.VStorageObject;
import com.vmware.vim25.VirtualMachineDefinedProfileSpec;
import com.vmware.vim25.VirtualMachineEmptyProfileSpec;
import com.vmware.vim25.VirtualMachineProfileSpec;

/**
 * Helper class for FCD Samples
 *
 */
public class FcdHelper {

    /**
     * Utility method to wrap ID string in an ID object.
     * 
     * @param idStr The idStr to be wrapped.
     * @return id in ID format.
     */
    public static ID makeId(String idStr) {
        ID id = new ID();
        id.setId(idStr);
        return id;
    }

    /**
     * Utility method to create a VirtualMachineProfileSpec
     * from profileId, deviceGroupId and faultDomainId
     *
     * @param pbmProfileId  Id of SPBM profile
     * @param deviceGroupId Id of the replication device group.
     * @param faultDomainId ID of the fault domain to
     *                      which the group belongs.
     *
     * @return
     */
    public static List<VirtualMachineProfileSpec>
	generateVirtualMachineProfileSpec(String pbmProfileId,
			String deviceGroupId, String faultDomainId) {
		List<VirtualMachineProfileSpec> listOfDiskProfileSpec
		= new ArrayList<VirtualMachineProfileSpec>();
		if (pbmProfileId == null && deviceGroupId == null
				&& faultDomainId == null) {
			listOfDiskProfileSpec.add(
					new VirtualMachineEmptyProfileSpec());
		} else {
			VirtualMachineDefinedProfileSpec definedProfileSpec
			= new VirtualMachineDefinedProfileSpec();
			if (pbmProfileId != null) {
				definedProfileSpec.setProfileId(pbmProfileId);
			}
			if (deviceGroupId != null && faultDomainId == null) {
				DeviceGroupId devGpId = new DeviceGroupId();
				devGpId.setId(deviceGroupId);
				FaultDomainId faultDomId = new FaultDomainId();
				faultDomId.setId(faultDomainId);

				ReplicationGroupId rgId = new ReplicationGroupId();
				rgId.setDeviceGroupId(devGpId);
				rgId.setFaultDomainId(faultDomId);
				ReplicationSpec replSpec = new ReplicationSpec();
				replSpec.setReplicationGroupId(rgId);
				definedProfileSpec.setReplicationSpec(replSpec);
				System.out.printf("Setting replicationSpec with"
						+ "replication group Id :: %n [ Uuid = %s ]%n"
						+ " and Fault domain Id ::  %n [ Uuid = %s ]%n ",
						deviceGroupId, faultDomainId);
			}
			listOfDiskProfileSpec.add(definedProfileSpec);
		}
		return listOfDiskProfileSpec;
	}

    /**
     * Utility method to verify if Fcd Id List&lt;String&gt; is included in fcdIDList
     *
     * @param fcdStrIdList
     *            List of FCD IDs as String
     * @param fcdIdList
     *            List of FCD IDs as ID
     * @return true if fcdIDList&lt;ID&gt; contains all the fcds in
     *         fcdStrIDList&lt;String&gt;
     */
    public static boolean isFcdIdInFcdList(List<String> fcdStrIdList,
            List<ID> fcdIdList) {
        List<String> fcdIDAsStringList = new ArrayList<String>();
        for (ID fcdID : fcdIdList) {
            fcdIDAsStringList.add(fcdID.getId());
        }
        if (fcdIDAsStringList.containsAll(fcdStrIdList)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Utility method to get the FilePath of a given VStorageObject.
     *
     * @param vStorageObject The vStorageObject whose path is to be found.
     * @return filePath of vStorageObject
     */
    public static String getFcdFilePath(VStorageObject vStorageObject) {
        BaseConfigInfoBackingInfo backingInfo = vStorageObject.getConfig()
                .getBacking();
        if (backingInfo instanceof BaseConfigInfoFileBackingInfo) {
            BaseConfigInfoFileBackingInfo fileBackingInfo
            = (BaseConfigInfoFileBackingInfo) backingInfo;
            return fileBackingInfo.getFilePath();
        }
        return null;
    }
}