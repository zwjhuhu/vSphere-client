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
 * HCIBatchAddHostAndExtendCluster
 *
 * This file includes sample codes for calling
 * batchAddHost HCI API to add new hosts to cluster.
 * At least four hosts are required for this sample.
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
 * run.bat com.vmware.hci.HCIBatchAddHostAndExtendCluster
 *    --url [webserviceurl]
 *    --username [username]
 *    --password [password]
 *    --hostIps [name]
 *    --hostUsername [hostUsername]
 *    --hostPassword [hostPassword]
 * </pre>
 */
@Sample(name = "HCIBatchAddHostAndExtendCluster",
        description = "This sample code demonstrates"
                    + " how to call vSphere HCI API")
public class HCIBatchAddHostAndExtendCluster extends ConnectedVimServiceBase
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
      throws InvalidPropertyFaultMsg,
             RuntimeFaultFaultMsg,
             InvalidCollectorVersionFaultMsg
   {
      boolean retVal = false;

      // info has a property - state of the task
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
      throws InvalidPropertyFaultMsg,
             RuntimeFaultFaultMsg
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
    * This function demonstrate an HCI example to add hosts
    * to cluster and extend the cluster.
    * This function first demonstrates how to use
    * batchAddHostsToCluster to add hosts into a cluster.
    * Then we call configHCI to config the HCI cluster.
    * Finally we call extendHCI to extend the existing cluster
    * with a couple more hosts.
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
             NotSupportedFaultMsg,
             VsanFaultFaultMsg,
             InvalidPropertyFaultMsg,
             RuntimeFaultFaultMsg,
             DuplicateNameFaultMsg,
             HostConnectFaultFaultMsg,
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

      System.out.println("Creating a cluster.");
      ClusterConfigSpecEx clusterConfigSpec = new ClusterConfigSpecEx();
      clusterConfigSpec.setInHciWorkflow(true);
      VsanClusterConfigInfo vsanConfig = new VsanClusterConfigInfo();
      vsanConfig.setEnabled(true);
      clusterConfigSpec.setVsanConfig(vsanConfig);
      vimPort.createClusterEx(hostFolderMor,
            this.clusterName, clusterConfigSpec);

      ManagedObjectReference cluster = getEntityFromName(serviceContent,
            "ClusterComputeResource", this.clusterName);

      List<FolderNewHostSpec> folderSpecs = new ArrayList<FolderNewHostSpec>();
      int half = hostIps.length/2;

      for (int i=0; i<half; i++) {
         String hostIp = hostIps[i];
         FolderNewHostSpec folderSpec = new FolderNewHostSpec();
         HostConnectSpec hostSpec = new HostConnectSpec();
         hostSpec.setHostName(hostIp);
         hostSpec.setUserName(this.hostUsername);
         hostSpec.setPassword(this.hostPassword);
         folderSpec.setHostCnxSpec(hostSpec);
         folderSpecs.add(folderSpec);
      }

      System.out.println("Batch add first half hosts.");
      // Batch all first half of hosts to cluster.
      ManagedObjectReference hciBatchAddHostTask =
            vimPort.batchAddHostsToClusterTask(hostFolderMor,
                                               cluster,
                                               folderSpecs,// new hosts
                                               null,       // existing hosts
                                               null,       // config spec
                                               null);      // desired state

      getTaskResultAfterDone(hciBatchAddHostTask);

      System.out.println("Config the cluster.");
      // Config the HCI cluster
      ClusterComputeResourceHCIConfigSpec hciSpec
            = new ClusterComputeResourceHCIConfigSpec();
      ClusterComputeResourceHostConfigurationProfile hostConfigProfile
            = new ClusterComputeResourceHostConfigurationProfile();

      HostLockdownMode lockdownMode = HostLockdownMode.LOCKDOWN_DISABLED;

      HostDateTimeConfig dateTimeConfig = new HostDateTimeConfig();
      String NTP_SERVER = "time-c-c.nist.gov";
      HostNtpConfig hostNtpConfig = new HostNtpConfig();
      List<String> serversFile = hostNtpConfig.getServer();
      serversFile.add(NTP_SERVER);
      dateTimeConfig.setNtpConfig(hostNtpConfig);
      hostConfigProfile.setLockdownMode(lockdownMode);
      hostConfigProfile.setDateTimeConfig(dateTimeConfig);

      hciSpec.setHostConfigProfile(hostConfigProfile);

      ManagedObjectReference hciTask
            = vimPort.configureHCITask(cluster, hciSpec, null);

      getTaskResultAfterDone(hciTask);

      // Extend the cluster with the second half hosts.
      List<ClusterComputeResourceHostConfigurationInput> hostInputs
            = new ArrayList<ClusterComputeResourceHostConfigurationInput>();

      // Get all Mo-IDs of firs half hosts
      ArrayOfManagedObjectReference existingHostMors =
               (ArrayOfManagedObjectReference) getMOREFs.entityProps(cluster,
                     new String[] {"host"}).get("host");

      List<ManagedObjectReference> existingHostList
                                  = existingHostMors.getManagedObjectReference();

      System.out.println("Adding second half hosts to the cluster.");
      // Add the rest of the hosts to data center
      for (int i=half; i<hostIps.length; i++) {
         String hostIp = hostIps[i];
         FolderNewHostSpec folderSpec = new FolderNewHostSpec();
         HostConnectSpec hostSpec = new HostConnectSpec();
         hostSpec.setHostName(hostIp);
         hostSpec.setUserName(hostUsername);
         hostSpec.setPassword(hostPassword);
         folderSpec.setHostCnxSpec(hostSpec);
         folderSpecs.add(folderSpec);
         ManagedObjectReference task = vimPort.addHostTask(cluster, hostSpec,
                  true, null, null);
         getTaskResultAfterDone(task);
      }

      // Get all Mo-IDs of all hosts
      ArrayOfManagedObjectReference allHostMors =
               (ArrayOfManagedObjectReference) getMOREFs.entityProps(cluster,
                     new String[] {"host"}).get("host");

      List<ManagedObjectReference> allHostList
                                  = allHostMors.getManagedObjectReference();

      // Leave the second half host Mo-IDs
      Set<String> rmMoIDSet = new HashSet<String>();
      for(ManagedObjectReference mor:existingHostList){
         rmMoIDSet.add(mor.getValue());
      }

      Set<ManagedObjectReference> rmSet
         = new HashSet<ManagedObjectReference>();
      for(ManagedObjectReference mor:allHostList){
         if(rmMoIDSet.contains(mor.getValue())) rmSet.add(mor);
      }

      allHostList.removeAll(rmSet);

      for(ManagedObjectReference hostMor : allHostList){
         ClusterComputeResourceHostConfigurationInput hostInput
               = new ClusterComputeResourceHostConfigurationInput();
         hostInput.setHost(hostMor);
         hostInputs.add(hostInput);
      }

      System.out.println("Extend the config of second half hosts.");
      ManagedObjectReference hciExtendClusterTask =
               vimPort.extendHCITask(cluster,
                                     hostInputs,  // host inputs
                                     null);       // vsan spec

      getTaskResultAfterDone(hciExtendClusterTask);
   }

}