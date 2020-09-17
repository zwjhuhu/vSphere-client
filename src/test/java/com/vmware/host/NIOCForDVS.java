/*
 * ******************************************************
 * Copyright VMware, Inc. 2010-2012,2018.  All Rights Reserved.
 * ******************************************************
 *
 * DISCLAIMER. THIS PROGRAM IS PROVIDED TO YOU "AS IS" WITHOUT
 * WARRANTIES OR CONDITIONS # OF ANY KIND, WHETHER ORAL OR WRITTEN,
 * EXPRESS OR IMPLIED. THE AUTHOR SPECIFICALLY # DISCLAIMS ANY IMPLIED
 * WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY # QUALITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.vmware.host;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

/**
 * <pre>
 *  This sample demonstrates how to add/modify NetworkResourcePool to Distributed
 *  Virtual Switch:
 *
 * <b>Parameters:</b>
 *  url               [required]: url of the web service.
 *  username          [required]: username for authentication
 *  password          [required]: password for authentication
 *  dvsname           [required]: Distributed Virtual Switch name
 *  enablenioc        : If true, enables I/O control. If false, disables network I/O control
 * <b>Sample usage:</b>
 *  Enable Network I/O Control:
 *  run.bat com.vmware.host.NIOCForDVS --url [URLString] --username [User] --password [Password]
 *  --dvsname [dvsname] --enablenioc [enablenioc]
 *
 *
 *  Note: Sample only works for DVS 5.0 onwards.
 * </pre>
 */
@Sample(
        name = "nioc-for-dvs",
        description = "demonstrates how to enable NIOC for a DVSwitch"
)
public class NIOCForDVS extends ConnectedVimServiceBase {

    private String dvsname = null;
    private String enableNIOC = null;

    @Option(name = "dvsname",required = true, description = "Distributed Virtual Switch name")
    public void setDvsname(String dvsname) {
        this.dvsname = dvsname;
    }

    @Option(name = "enablenioc", required = true , description = "If true, enables I/O control. If false, disables network I/O control")
    public void setEnableNIOC(String enableNIOC) {
        this.enableNIOC = enableNIOC;
    }

    /**
     * Enable/Disable network I/O control on the vSphere Distributed Switch.
     *
     * @param dvSwitchName VmwareDistributedVirtualSwitch name
     * @param enableNIOC   boolean; true, enables I/O control. false, disables network I/O
     *                     control.
     * @throws DvsFaultFaultMsg        Thrown if the enabling/disabling fails.
     * @throws RuntimeFaultFaultMsg    Thrown if any type of runtime fault is thrown that is not
     *                                 covered by the other faults; for example, a communication
     *                                 error.
     * @throws InvalidPropertyFaultMsg
     */
    void enableNIOC(String dvSwitchName, boolean enableNIOC)
            throws DvsFaultFaultMsg, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference dvsMor =
                getMOREFs.inFolderByType(serviceContent.getRootFolder(),
                        "VmwareDistributedVirtualSwitch").get(dvSwitchName);
        if (dvsMor != null) {
            vimPort.enableNetworkResourceManagement(dvsMor, enableNIOC);
            System.out.printf("Set network I/O control");
        } else {
            System.out.println("DVS Switch " + dvSwitchName + " Not Found");
            return;
        }
    }


    @Action
    public void run() throws InvalidPropertyFaultMsg, DvsFaultFaultMsg, RuntimeFaultFaultMsg {
            enableNIOC(dvsname, Boolean.parseBoolean(enableNIOC));
    }
}
