/*
 * ******************************************************
 * Copyright VMware, Inc. 2018.  All Rights Reserved.
 * ******************************************************
 *
 * DISCLAIMER. THIS PROGRAM IS PROVIDED TO YOU "AS IS" WITHOUT
 * WARRANTIES OR CONDITIONS # OF ANY KIND, WHETHER ORAL OR WRITTEN,
 * EXPRESS OR IMPLIED. THE AUTHOR SPECIFICALLY # DISCLAIMS ANY IMPLIED
 * WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY # QUALITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.vmware.hci;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import java.util.*;

/**
 * <pre>
 * HCIComputeOnlyCluster
 *
 * This file includes sample codes for calling
 * config HCI API with simple config.
 *
 * This example demonstrates how to call the vSphere HCI API via Vsphere SDK.
 *
 * <b>Parameters:</b>
 * url          [required] : URL of the vCenter Web Service
 * username     [required] : Username for the vCenter authentication
 * password     [required] : Password for the vCenter authentication
 * hostIps      [required] : IPs of the hosts to be added to the cluster
 * hostUsername [required] : Username of the hosts
 * hostPassword [required] : Password of the hosts
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.hci.HCIComputeOnlyCluster
 *    --url [webserviceurl]
 *    --username [username]
 *    --password [password]
 *    --hostIps [name]
 *    --hostUsername [hostUsername]
 *    --hostPassword [hostPassword]
 * </pre>
 */
@Sample(name = "HCIComputeOnlyCluster",
        description = "This sample code demonstrates"
                 + " how to call vSphere HCI API")
public class HCIComputeOnlyCluster extends ConnectedVimServiceBase
{
   private final String datacenterName = "Datacenter";
   private final String clusterName = "HCI-Cluster";

   private String[] hostIps;
   private String hostUsername;
   private String hostPassword;

   @Option(name = "hostIps", required = true,
           description = "The IPs of the hosts, comma separated")
   public void sethostIps(String hostIps)
   {
      this.hostIps = hostIps.split(",");
   }

   @Option(name = "hostUsername", required = true,
           description = "The username for the hosts to be added")
   public void sethostUsername(String hostUsername)
   {
      this.hostUsername = hostUsername;
   }

   @Option(name = "hostPassword", required = true,
           description = "The password for the hosts to be added")
   public void sethostPassword(String hostPassword)
   {
      this.hostPassword = hostPassword;
   }

   /**
    * This method returns a boolean value specifying whether the Task is
    * succeeded or failed.
    *
    * @param task ManagedObjectReference representing the Task.
    * @return boolean value representing the Task result.
    * @throws InvalidCollectorVersionFaultMsg
    * @throws RuntimeFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    */
   boolean getTaskResultAfterDone(ManagedObjectReference task)
           throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg,
           InvalidCollectorVersionFaultMsg
   {

       boolean retVal = false;

       // info has a property - state for state of the task
       Object[] result =
              waitForValues.wait(task, new String[]{"info.state", "info.error"},
                      new String[]{"state"}, new Object[][]{new Object[]{
                      TaskInfoState.SUCCESS, TaskInfoState.ERROR}});

       if (result[0].equals(TaskInfoState.SUCCESS)) {
           retVal = true;
       }
       if (result[1] instanceof LocalizedMethodFault) {
           throw new RuntimeException(
                   ((LocalizedMethodFault) result[1]).getLocalizedMessage());
       }
       return retVal;
   }

   private ManagedObjectReference
           getEntityFromName(ServiceContent serviceContent,
                                  String entityType,
                                  String entityName)
      throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg
   {
      Map<String, ManagedObjectReference> entities =
                   getMOREFs.inContainerByType(serviceContent.getRootFolder(),
                                                entityType);
      for (String eName : entities.keySet()) {
         if (eName.equals(entityName)) {
            return entities.get(entityName);
         }
      }
      return null;
   }

   /**
    * This function demostrate a simple configHCI function call
    * to config the host with NTP server and lockdown mode.
    * This an example of showing the usuage of compute-only cluster
    * with some simple config.
    *
    * @throws NotFoundFaultMsg
    * @throws NotSupportedFaultMsg
    * @throws VsanFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    * @throws RuntimeFaultFaultMsg
    * @throws HostConnectFaultFaultMsg
    * @throws DuplicateNameFaultMsg
    * @throws InvalidLoginFaultMsg
    * @throws InvalidNameFaultMsg
    * @throws InvalidCollectorVersionFaultMsg
    */
   @Action
   public void main()
         throws NotFoundFaultMsg,
                NotSupportedFaultMsg, VsanFaultFaultMsg,
                InvalidPropertyFaultMsg,
                RuntimeFaultFaultMsg,
                DuplicateNameFaultMsg, HostConnectFaultFaultMsg,
                InvalidNameFaultMsg,
                InvalidLoginFaultMsg,
                InvalidCollectorVersionFaultMsg
   {
      AboutInfo aboutInfo = serviceContent.getAbout();

      if (!aboutInfo.getApiType().equals("VirtualCenter")) {
         System.out.println("Configure HCI API is only supported on vCenter");
         System.exit(1);
      }

      String apiVersion = aboutInfo.getApiVersion();
      if (apiVersion.compareTo("6.7") < 0) {
         System.out.format(
            "The Virtual Center with version %s "
            + "(lower than 6.7) is not supported.",
            aboutInfo.getApiVersion());
         return;
       }

      connect();

      System.out.println("Creating a datacenter.");
      vimPort.createDatacenter(
               serviceContent.getRootFolder(),
               this.datacenterName);

      ManagedObjectReference dcMor = getEntityFromName(serviceContent,
            "Datacenter", this.datacenterName);
      ManagedObjectReference hostFolderMor =
            (ManagedObjectReference) getMOREFs.entityProps(dcMor,
                  new String[] {"hostFolder"}).get("hostFolder");
      ClusterConfigSpecEx clusterConfigSpec = new ClusterConfigSpecEx();
      clusterConfigSpec.setInHciWorkflow(true);
      VsanClusterConfigInfo vsanConfig = new VsanClusterConfigInfo();
      vsanConfig.setEnabled(true);
      clusterConfigSpec.setVsanConfig(vsanConfig);
      System.out.println("Creating the cluster.");
      vimPort.createClusterEx(hostFolderMor,
            this.clusterName, clusterConfigSpec);

      ManagedObjectReference cluster = getEntityFromName(serviceContent,
            "ClusterComputeResource", this.clusterName);

      System.out.println("Adding hosts to the cluster.");
      for (String hostIp : hostIps) {
         HostConnectSpec hostSpec = new HostConnectSpec();
         hostSpec.setHostName(hostIp);
         hostSpec.setUserName(this.hostUsername);
         hostSpec.setPassword(this.hostPassword);
         ManagedObjectReference task =
               vimPort.addHostTask(cluster,
                     hostSpec, true, null, null);
         getTaskResultAfterDone(task);
      }

      ClusterComputeResourceHCIConfigSpec hciSpec
                        = new ClusterComputeResourceHCIConfigSpec();
      ClusterComputeResourceHostConfigurationProfile hostConfigProfile
                        = new ClusterComputeResourceHostConfigurationProfile();

      HostLockdownMode lockdownMode = HostLockdownMode.LOCKDOWN_DISABLED;

      HostDateTimeConfig dateTimeConfig = new HostDateTimeConfig();
      String NTP_SERVER = "time-c-b.nist.gov";
      HostNtpConfig hostNtpConfig = new HostNtpConfig();
      List<String> serversFile = hostNtpConfig.getServer();
      serversFile.add(NTP_SERVER);
      dateTimeConfig.setNtpConfig(hostNtpConfig);
      hostConfigProfile.setLockdownMode(lockdownMode);
      hostConfigProfile.setDateTimeConfig(dateTimeConfig);

      hciSpec.setHostConfigProfile(hostConfigProfile);

      System.out.println("Config the cluster.");
      ManagedObjectReference hciTask =
            vimPort.configureHCITask(cluster, hciSpec, null);

      getTaskResultAfterDone(hciTask);
   }
}