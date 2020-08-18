package com.github.kubesys.vsphere;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Optional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Encoder;

/**
 * Hello world!
 *
 */
public class App 
{
	static {
		disableSslVerification();
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (

		NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	private String session;
	private String vcip;
	private String vcUsername;
	private String vcPassword;
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper objectMapper = new ObjectMapper();

	public App(String vcip, String vcUsername, String vcPassword) {
		this.vcip = vcip;
		this.vcUsername = vcUsername;
		this.vcPassword = vcPassword;
		createAPISession();
	}

	public void createAPISession() {
		System.out.println("Creating API Session..." + vcip);
		String url = "https://" + vcip + "/rest/com/vmware/cis/session";
		System.out.println(url);
		String authString = vcUsername + ":" + vcPassword;
		String base64Creds = new BASE64Encoder().encode(authString.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		ResponseEntity<Session> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				new HttpEntity<String>(headers), Session.class);
		session = responseEntity.getBody().getValue();
		System.out.println("session::" + session);
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class Session {
		@JsonProperty("value")
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public HttpHeaders getHttpHeaders() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		if (session != null) {
			headers.set("Cookie", "vmware-api-session-id= " + session);
		}
		return headers;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class Clusters {
		@JsonProperty("value")
		private Cluster[] value;

		public Cluster[] getValue() {
			return value;
		}

		public void setValue(Cluster[] value) {
			this.value = value;
		}

		@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
		public static class Cluster {
			@JsonProperty("cluster")
			private String cluster;

			public String getCluster() {
				return cluster;
			}

			public void setCluster(String cluster) {
				this.cluster = cluster;
			}
		}
	}

	/**
	 * This method is used to get the cluster MOID from the cluster name
	 * 
	 * @param clusterName
	 * @return
	 * @throws Exception
	 */
	public String getClusterMOID(String clusterName) throws Exception {
		System.out.println("Enter into getClusterMOID::" + clusterName);
		String clusterMoid = null;
		try {
			String url = "https://" + vcip + "/rest/vcenter/cluster?filter.names=" + clusterName;
			System.out.println(url);
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<Clusters> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getReq,
					Clusters.class);
			Clusters cluster = responseEntity.getBody();
			clusterMoid = cluster.getValue()[0].getCluster();
			System.out.println("clusterMoid::" + clusterMoid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusterMoid;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class Networks {
		@JsonProperty("value")
		private Network[] value;

		public Network[] getValue() {
			return value;
		}

		public void setValue(Network[] value) {
			this.value = value;
		}

		@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
		public static class Network {
			@JsonProperty("network")
			private String network;

			public String getNetwork() {
				return network;
			}

			public void setNetwork(String network) {
				this.network = network;
			}
		}
	}

	/**
	 * This method is used to get the network MOID from the network name
	 * 
	 * @param networkName
	 * @return
	 * @throws Exception
	 */
	public String getNetworkMOID(String networkName) throws Exception {
		System.out.println("Enter into getNetworkMOID::" + networkName);
		String networkMoid = null;
		try {
			String url = "https://" + vcip + "/rest/vcenter/network?filter.names=" + networkName;
			System.out.println(url);
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<Networks> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getReq,
					Networks.class);
			Networks response = responseEntity.getBody();
			networkMoid = response.getValue()[0].getNetwork();
			System.out.println("networkMoid::" + networkMoid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return networkMoid;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class StoragePolicyObj {
		@JsonProperty("name")
		private String name;
		@JsonProperty("policy")
		private String policy;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPolicy() {
			return policy;
		}

		public void setPolicy(String policy) {
			this.policy = policy;
		}
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class StoragePoliciesObj {
		@JsonProperty("value")
		private StoragePolicyObj[] value;

		public StoragePolicyObj[] getValue() {
			return value;
		}

		public void setValue(StoragePolicyObj[] value) {
			this.value = value;
		}

	}

	/**
	 * This method is used to get the VM storage policy ID from the storage
	 * policy name
	 * 
	 * @param policyName
	 * @return
	 * @throws Exception
	 */
	public String getStoragePolicyID(String policyName) throws Exception {
		System.out.println("Enter into getStoragePolicyID::" + policyName);
		String storagePolicyID = null;
		try {
			String url = "https://" + vcip + "/rest/vcenter/storage/policies";
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<StoragePoliciesObj> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getReq,
					StoragePoliciesObj.class);
			StoragePoliciesObj storagePolicies = responseEntity.getBody();
			Optional<StoragePolicyObj> value = Arrays.stream(storagePolicies.getValue())
					.filter(policy -> (policy.getName().equals(policyName))).findFirst();
			storagePolicyID = value.get().getPolicy();
			System.out.println("storagePolicyID::" + storagePolicyID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storagePolicyID;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class DistributedSwitchCompatibility {
		@JsonProperty("compatible")
		private boolean compatible;
		@JsonProperty("incompatibility_reasons")
		private String[] incompatibility_reasons;
		@JsonProperty("distributed_switch")
		private String distributed_switch;
		@JsonProperty("network_provider")
		private String network_provider;

		public boolean isCompatible() {
			return compatible;
		}

		public void setCompatible(boolean compatible) {
			this.compatible = compatible;
		}

		public String[] getIncompatibility_reasons() {
			return incompatibility_reasons;
		}

		public void setIncompatibility_reasons(String[] incompatibility_reasons) {
			this.incompatibility_reasons = incompatibility_reasons;
		}

		public String getDistributed_switch() {
			return distributed_switch;
		}

		public void setDistributed_switch(String distributed_switch) {
			this.distributed_switch = distributed_switch;
		}

		public String isNetwork_provider() {
			return network_provider;
		}

		public void setNetwork_provider(String network_provider) {
			this.network_provider = network_provider;
		}
	}

	/**
	 * This method is used to get the compatible virtual distributed switch from
	 * the provided cluster
	 * 
	 * @param clusterMoid
	 * @return
	 * @throws Exception
	 */
	public DistributedSwitchCompatibility[] getCompatibleDistributedSwitch(String clusterMoid) throws Exception {
		System.out.println("Enter into getCompatibleDistributedSwitch::" + clusterMoid);
		DistributedSwitchCompatibility[] dvsCompatibleList = null;
		try {
			String url = "https://" + vcip
					+ "/api/vcenter/namespace-management/distributed-switch-compatibility?cluster=" + clusterMoid
					+ "&compatible=true";
			System.out.println(url);
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<DistributedSwitchCompatibility[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					getReq, DistributedSwitchCompatibility[].class);
			dvsCompatibleList = responseEntity.getBody();
			String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dvsCompatibleList);
			System.out.println("DistributedSwitch Compatibility response::\n" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dvsCompatibleList;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class EdgeclusterCompatibility {
		@JsonProperty("compatible")
		private boolean compatible;
		@JsonProperty("edge_cluster")
		private String edge_cluster;
		@JsonProperty("display_name")
		private String display_name;

		public boolean isCompatible() {
			return compatible;
		}

		public void setCompatible(boolean compatible) {
			this.compatible = compatible;
		}

		public String getEdge_cluster() {
			return edge_cluster;
		}

		public void setEdge_cluster(String edge_cluster) {
			this.edge_cluster = edge_cluster;
		}

		public String getDisplay_name() {
			return display_name;
		}

		public void setDisplay_name(String display_name) {
			this.display_name = display_name;
		}
	}

	/**
	 * This method is used to get the compatible edgecluster details for the
	 * virtual distributed switch(VDS).
	 * 
	 * @param clusterMoid
	 * @param dvsUUID
	 * @param edgeCluster
	 *            is Optional
	 * @return
	 * @throws Exception
	 */
	public EdgeclusterCompatibility getCompatibleEdgeCluster(String clusterMoid, String dvsUUID, String edgeCluster)
			throws Exception {
		System.out
				.println("Enter into getCompatibleEdgeCluster::" + clusterMoid + " , " + dvsUUID + " , " + edgeCluster);
		EdgeclusterCompatibility edgeClusterCompatible = null;
		try {
			String url = "https://" + vcip + "/api/vcenter/namespace-management/edge-cluster-compatibility?cluster="
					+ clusterMoid + "&distributed_switch=" + dvsUUID + "&compatible=true";
			System.out.println(url);
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<EdgeclusterCompatibility[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					getReq, EdgeclusterCompatibility[].class);
			EdgeclusterCompatibility[] edgeClusterCompatibleList = responseEntity.getBody();
			String jsonStr = objectMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(edgeClusterCompatibleList);
			System.out.println("Edgecluster Compatibility response::\n" + jsonStr);
			if (edgeClusterCompatibleList != null && edgeClusterCompatibleList.length > 0) {
				for (EdgeclusterCompatibility edgeClusterCmp : edgeClusterCompatibleList) {
					if (edgeCluster == null) {
						edgeClusterCompatible = edgeClusterCmp;
						break;
					} else if (edgeClusterCmp.getDisplay_name().equals(edgeCluster)) {
						edgeClusterCompatible = edgeClusterCmp;
						break;
					}
				}
			}
			if (edgeClusterCompatible == null) {
				System.err.println("Error: There is no compatible edgecluster for the VDS-" + dvsUUID
						+ " in the cluster " + clusterMoid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return edgeClusterCompatible;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class ClusterCompatibility {
		@JsonProperty("cluster")
		private String cluster;
		@JsonProperty("incompatibility_reasons")
		private Object[] incompatibility_reasons;
		@JsonProperty("compatible")
		private boolean compatible;

		public String getCluster() {
			return cluster;
		}

		public void setCluster(String cluster) {
			this.cluster = cluster;
		}

		public Object[] getIncompatibility_reasons() {
			return incompatibility_reasons;
		}

		public void setIncompatibility_reasons(Object[] incompatibility_reasons) {
			this.incompatibility_reasons = incompatibility_reasons;
		}

		public boolean getCompatible() {
			return compatible;
		}

		public void setCompatible(boolean compatible) {
			this.compatible = compatible;
		}

	}

	/**
	 * This method is used to get the Cluster compatibility details
	 * 
	 * @param clusterMoid
	 * @return
	 */
	public ClusterCompatibility getClusterCompatibility(String clusterMoid) {
		System.out.println("Enter into getClusterCompatibility::" + clusterMoid);
		ClusterCompatibility response = null;
		try {
			String url = "https://" + vcip + "/api/vcenter/namespace-management/cluster-compatibility";
			System.out.println(url);
			HttpEntity<String> getReq = new HttpEntity<>("", getHttpHeaders());
			ResponseEntity<ClusterCompatibility[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getReq,
					ClusterCompatibility[].class);
			ClusterCompatibility[] clusters = responseEntity.getBody();
			for (ClusterCompatibility cluster : clusters) {
				if (cluster != null && cluster.getCluster().equals(clusterMoid)) {
					response = cluster;
					String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
					System.out.println("Cluster Compatibility response::\n" + jsonStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * This method is used to enable vSphere Kubernetes cluster using the spec
	 * 
	 * @param enableSpec
	 * @param clusterMoid
	 * @return
	 * @throws Exception
	 */
	public String enableVsphereKubernetesClusterAPI(EnableSpec enableSpec, String clusterMoid) throws Exception {
		System.out.println("Enter into enableVsphereKubernetesClusterAPI::" + clusterMoid);
		String response = null;
		try {
			String url = "https://" + vcip + "/api/vcenter/namespace-management/clusters/" + clusterMoid
					+ "?action=enable";
			System.out.println(url);
			HttpEntity<EnableSpec> postReq = new HttpEntity<EnableSpec>(enableSpec, getHttpHeaders());
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, postReq, String.class);
			response = responseEntity.getBody();
			System.out.println(
					"Successfully invoked enabling vsphere Kubernetes cluster, pls check UI or use GET API to check cluster configuration status::"
							+ "https://" + vcip + "/api/vcenter/namespace-management/clusters/" + clusterMoid);
		} catch (Exception e) {
			System.err.println("Error::" + e.getMessage() + ", Check existing status for the cluster using GET call:: "
					+ "https://" + vcip + "/api/vcenter/namespace-management/clusters/" + clusterMoid);
		}
		return response;
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class EnableSpec {
		@JsonProperty("size_hint")
		private String sizeHint;
		@JsonProperty("service_cidr")
		private ServiceCidr serviceCidr;
		@JsonProperty("master_management_network")
		private MasterManagementNetwork masterManagementNetwork;
		@JsonProperty("master_DNS")
		private String[] masterDNS;
		@JsonProperty("master_DNS_search_domains")
		private String[] masterDNSSearchDomains;
		@JsonProperty("master_NTP_servers")
		private String[] masterNTPServers;
		@JsonProperty("master_storage_policy")
		private String masterStoragePolicy;
		@JsonProperty("worker_DNS")
		private String[] workerDNS;
		@JsonProperty("ephemeral_storage_policy")
		private String ephemeralStoragePolicy;
		@JsonProperty("image_storage")
		private ImageStorage imageStorage;
		@JsonProperty("ncp_cluster_network_spec")
		private NcpClusterNetworkSpec ncpClusterNetworkSpec;
		@JsonProperty("network_provider")
		private String networkProvider;
		@JsonProperty("login_banner")
		private String login_banner;

		public String getLogin_banner() {
			return login_banner;
		}

		public void setLogin_banner(String login_banner) {
			this.login_banner = login_banner;
		}

		public String getSizeHint() {
			return sizeHint;
		}

		public void setSizeHint(String sizeHint) {
			this.sizeHint = sizeHint;
		}

		public ServiceCidr getServiceCidr() {
			return serviceCidr;
		}

		public void setServiceCidr(ServiceCidr serviceCidr) {
			this.serviceCidr = serviceCidr;
		}

		public MasterManagementNetwork getMasterManagementNetwork() {
			return masterManagementNetwork;
		}

		public void setMasterManagementNetwork(MasterManagementNetwork masterManagementNetwork) {
			this.masterManagementNetwork = masterManagementNetwork;
		}

		public String[] getMasterDNS() {
			return masterDNS;
		}

		public void setMasterDNS(String[] masterDNS) {
			this.masterDNS = masterDNS;
		}

		public String[] getMasterDNSSearchDomains() {
			return masterDNSSearchDomains;
		}

		public void setMasterDNSSearchDomains(String[] masterDNSSearchDomains) {
			this.masterDNSSearchDomains = masterDNSSearchDomains;
		}

		public String[] getMasterNTPServers() {
			return masterNTPServers;
		}

		public void setMasterNTPServers(String[] masterNTPServers) {
			this.masterNTPServers = masterNTPServers;
		}

		public String getMasterStoragePolicy() {
			return masterStoragePolicy;
		}

		public void setMasterStoragePolicy(String masterStoragePolicy) {
			this.masterStoragePolicy = masterStoragePolicy;
		}

		public String[] getWorkerDNS() {
			return workerDNS;
		}

		public void setWorkerDNS(String[] workerDNS) {
			this.workerDNS = workerDNS;
		}

		public String getEphemeralStoragePolicy() {
			return ephemeralStoragePolicy;
		}

		public void setEphemeralStoragePolicy(String ephemeralStoragePolicy) {
			this.ephemeralStoragePolicy = ephemeralStoragePolicy;
		}

		public ImageStorage getImageStorage() {
			return imageStorage;
		}

		public void setImageStorage(ImageStorage imageStorage) {
			this.imageStorage = imageStorage;
		}

		public NcpClusterNetworkSpec getNcpClusterNetworkSpec() {
			return ncpClusterNetworkSpec;
		}

		public void setNcpClusterNetworkSpec(NcpClusterNetworkSpec ncpClusterNetworkSpec) {
			this.ncpClusterNetworkSpec = ncpClusterNetworkSpec;
		}

		public String getNetworkProvider() {
			return networkProvider;
		}

		public void setNetworkProvider(String networkProvider) {
			this.networkProvider = networkProvider;
		}

	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class NcpClusterNetworkSpec {

		@JsonProperty("pod_cidrs")
		private PodCidr[] podCidrs = null;
		@JsonProperty("ingress_cidrs")
		private IngressCidr[] ingressCidrs = null;
		@JsonProperty("egress_cidrs")
		private EgressCidr[] egressCidrs = null;
		@JsonProperty("cluster_distributed_switch")
		private String clusterDistributedSwitch;
		@JsonProperty("nsx_edge_cluster")
		private String nsxEdgeCluster;

		public PodCidr[] getPodCidrs() {
			return podCidrs;
		}

		public void setPodCidrs(PodCidr[] podCidrs) {
			this.podCidrs = podCidrs;
		}

		public IngressCidr[] getIngressCidrs() {
			return ingressCidrs;
		}

		public void setIngressCidrs(IngressCidr[] ingressCidrs) {
			this.ingressCidrs = ingressCidrs;
		}

		public EgressCidr[] getEgressCidrs() {
			return egressCidrs;
		}

		public void setEgressCidrs(EgressCidr[] egressCidrs) {
			this.egressCidrs = egressCidrs;
		}

		public String getClusterDistributedSwitch() {
			return clusterDistributedSwitch;
		}

		public void setClusterDistributedSwitch(String clusterDistributedSwitch) {
			this.clusterDistributedSwitch = clusterDistributedSwitch;
		}

		public String getNsxEdgeCluster() {
			return nsxEdgeCluster;
		}

		public void setNsxEdgeCluster(String nsxEdgeCluster) {
			this.nsxEdgeCluster = nsxEdgeCluster;
		}
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class PodCidr {
		@JsonProperty("address")
		private String address;
		@JsonProperty("prefix")
		private int prefix;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public class ImageStorage {
		@JsonProperty("storage_policy")
		private String storagePolicy;

		@JsonProperty("storagePolicy")
		public String getStoragePolicy() {
			return storagePolicy;
		}

		@JsonProperty("storagePolicy")
		public void setStoragePolicy(String storagePolicy) {
			this.storagePolicy = storagePolicy;
		}

	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServiceCidr {
		@JsonProperty("address")
		private String address;
		@JsonProperty("prefix")
		private int prefix;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}

	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class MasterManagementNetwork {
		@JsonProperty("network")
		private String network;
		@JsonProperty("mode")
		private String mode;
		@JsonProperty("floating_IP")
		private String floating_IP;
		@JsonProperty("address_range")
		private AddressRange addressRange;

		public String getFloating_IP() {
			return floating_IP;
		}

		public void setFloating_IP(String floating_IP) {
			this.floating_IP = floating_IP;
		}

		public String getNetwork() {
			return network;
		}

		public void setNetwork(String network) {
			this.network = network;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public AddressRange getAddressRange() {
			return addressRange;
		}

		public void setAddressRange(AddressRange addressRange) {
			this.addressRange = addressRange;
		}

	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class IngressCidr {
		@JsonProperty("address")
		private String address;
		@JsonProperty("prefix")
		private int prefix;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}

	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public static class AddressRange {
		@JsonProperty("starting_address")
		private String startingAddress;
		@JsonProperty("address_count")
		private int addressCount;
		@JsonProperty("subnet_mask")
		private String subnetMask;
		@JsonProperty("gateway")
		private String gateway;

		public String getStartingAddress() {
			return startingAddress;
		}

		public void setStartingAddress(String startingAddress) {
			this.startingAddress = startingAddress;
		}

		public int getAddressCount() {
			return addressCount;
		}

		public void setAddressCount(int addressCount) {
			this.addressCount = addressCount;
		}

		public String getSubnetMask() {
			return subnetMask;
		}

		public void setSubnetMask(String subnetMask) {
			this.subnetMask = subnetMask;
		}

		public String getGateway() {
			return gateway;
		}

		public void setGateway(String gateway) {
			this.gateway = gateway;
		}
	}

	@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
	public class EgressCidr {
		@JsonProperty("address")
		private String address;
		@JsonProperty("prefix")
		private int prefix;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}

	}

	public static class VsphereKubernetesClusterBean {
		private String clusterName;
		private String sizeHint;
		private String networkName;
		private String mode;
		private String floatingIP;
		private String startingAddress;
		private int addressCount;
		private String subnetMask;
		private String gateway;
		private String masterDNS[];
		private String masterDNSSearchDomains[];
		private String masterNTPServers[];
		private String nsxEdgeClusterName;
		private String workerDNS[];
		private String podCidrs[];
		private String serviceCidr;
		private String ingressCidrs[];
		private String egressCidrs[];
		private String storagePolicy;

		public String getClusterName() {
			return clusterName;
		}

		public void setClusterName(String clusterName) {
			this.clusterName = clusterName;
		}

		public String getSizeHint() {
			return sizeHint;
		}

		public void setSizeHint(String sizeHint) {
			this.sizeHint = sizeHint;
		}

		public String getNetworkName() {
			return networkName;
		}

		public void setNetworkName(String networkName) {
			this.networkName = networkName;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public String getStartingAddress() {
			return startingAddress;
		}

		public void setStartingAddress(String startingAddress) {
			this.startingAddress = startingAddress;
		}

		public int getAddressCount() {
			return addressCount;
		}

		public void setAddressCount(int addressCount) {
			this.addressCount = addressCount;
		}

		public String getSubnetMask() {
			return subnetMask;
		}

		public void setSubnetMask(String subnetMask) {
			this.subnetMask = subnetMask;
		}

		public String getGateway() {
			return gateway;
		}

		public void setGateway(String gateway) {
			this.gateway = gateway;
		}

		public String[] getMasterDNS() {
			return masterDNS;
		}

		public void setMasterDNS(String[] masterDNS) {
			this.masterDNS = masterDNS;
		}

		public String[] getMasterDNSSearchDomains() {
			return masterDNSSearchDomains;
		}

		public void setMasterDNSSearchDomains(String[] masterDNSSearchDomains) {
			this.masterDNSSearchDomains = masterDNSSearchDomains;
		}

		public String[] getMasterNTPServers() {
			return masterNTPServers;
		}

		public void setMasterNTPServers(String[] masterNTPServers) {
			this.masterNTPServers = masterNTPServers;
		}

		public String getNsxEdgeClusterName() {
			return nsxEdgeClusterName;
		}

		public void setNsxEdgeClusterName(String nsxEdgeClusterName) {
			this.nsxEdgeClusterName = nsxEdgeClusterName;
		}

		public String[] getWorkerDNS() {
			return workerDNS;
		}

		public void setWorkerDNS(String[] workerDNS) {
			this.workerDNS = workerDNS;
		}

		public String[] getPodCidrs() {
			return podCidrs;
		}

		public void setPodCidrs(String[] podCidrs) {
			this.podCidrs = podCidrs;
		}

		public String getServiceCidr() {
			return serviceCidr;
		}

		public void setServiceCidr(String serviceCidr) {
			this.serviceCidr = serviceCidr;
		}

		public String[] getIngressCidrs() {
			return ingressCidrs;
		}

		public void setIngressCidrs(String[] ingressCidrs) {
			this.ingressCidrs = ingressCidrs;
		}

		public String[] getEgressCidrs() {
			return egressCidrs;
		}

		public void setEgressCidrs(String[] egressCidrs) {
			this.egressCidrs = egressCidrs;
		}

		public String getStoragePolicy() {
			return storagePolicy;
		}

		public void setStoragePolicy(String storagePolicy) {
			this.storagePolicy = storagePolicy;
		}

		public String getFloatingIP() {
			return floatingIP;
		}

		public void setFloatingIP(String floatingIP) {
			this.floatingIP = floatingIP;
		}

		public VsphereKubernetesClusterBean() {

		}

		public VsphereKubernetesClusterBean(String clusterName, String sizeHint, String networkName, String mode,
				String floatingIP, String startingAddress, int addressCount, String subnetMask, String gateway,
				String[] masterDNS, String[] masterDNSSearchDomains, String[] masterNTPServers,
				String nsxEdgeClusterName, String[] workerDNS, String[] podCidrs, String serviceCidr,
				String[] ingressCidrs, String[] egressCidrs, String storagePolicy) {
			super();
			this.clusterName = clusterName;
			this.sizeHint = sizeHint;
			this.networkName = networkName;
			this.mode = mode;
			this.floatingIP = floatingIP;
			this.startingAddress = startingAddress;
			this.addressCount = addressCount;
			this.subnetMask = subnetMask;
			this.gateway = gateway;
			this.masterDNS = masterDNS;
			this.masterDNSSearchDomains = masterDNSSearchDomains;
			this.masterNTPServers = masterNTPServers;
			this.nsxEdgeClusterName = nsxEdgeClusterName;
			this.workerDNS = workerDNS;
			this.podCidrs = podCidrs;
			this.serviceCidr = serviceCidr;
			this.ingressCidrs = ingressCidrs;
			this.egressCidrs = egressCidrs;
			this.storagePolicy = storagePolicy;
			if (this.mode.equals("STATICRANGE")) {
				if (this.startingAddress == null || this.subnetMask == null || this.gateway == null
						|| this.addressCount == 0) {
					System.err.println(
							"Error::For STATICRANGE mode , startingAddress,addressCount,subnetMask and gateway fields are mandatory.Re-try by setting those feilds.");
					System.exit(0);
				}
			} else {
				if (this.floatingIP == null || this.floatingIP.isEmpty()) {
					System.err.println("Error::For DHCP mode , FloatingIP is mandatory.Re-try by setting floatingIP.");
					System.exit(0);
				}
			}
		}

		@Override
		public String toString() {
			return "VsphereKubernetesClusterBean [clusterName=" + clusterName + ", sizeHint=" + sizeHint
					+ ", networkName=" + networkName + ", mode=" + mode + ", floatingIP=" + floatingIP
					+ ", startingAddress=" + startingAddress + ", addressCount=" + addressCount + ", subnetMask="
					+ subnetMask + ", gateway=" + gateway + ", masterDNS=" + Arrays.toString(masterDNS)
					+ ", masterDNSSearchDomains=" + Arrays.toString(masterDNSSearchDomains) + ", masterNTPServers="
					+ Arrays.toString(masterNTPServers) + ", nsxEdgeClusterName=" + nsxEdgeClusterName + ", workerDNS="
					+ Arrays.toString(workerDNS) + ", podCidrs=" + Arrays.toString(podCidrs) + ", serviceCidr="
					+ serviceCidr + ", ingressCidrs=" + Arrays.toString(ingressCidrs) + ", egressCidrs="
					+ Arrays.toString(egressCidrs) + ", storagePolicy=" + storagePolicy + "]";
		}

	}

	/**
	 * This method is used to enable vSphere Kubernetes cluster
	 * 
	 * @param vkcBean
	 * @return
	 */
	public void enableVsphereKubernetesCluster(VsphereKubernetesClusterBean vkcBean) {
		System.out.println("Enter into enableVsphereKubernetesCluster...");
		System.out.println(vkcBean.toString());
		String clusterMoid = null;
		try {
			clusterMoid = getClusterMOID(vkcBean.getClusterName());
			ClusterCompatibility clusterCompatibility = getClusterCompatibility(clusterMoid);
			if (clusterCompatibility != null && clusterCompatibility.getCompatible()) {
				EnableSpec enableSpec = new EnableSpec();
				enableSpec.setSizeHint(vkcBean.getSizeHint());
				enableSpec.setLogin_banner("Enable vSphere Kubernetes Cluster..." + vkcBean.getClusterName());
				ServiceCidr serviceCidr = new ServiceCidr();
				String[] sCidr = vkcBean.getServiceCidr().split("/");
				serviceCidr.setAddress(sCidr[0]);
				serviceCidr.setPrefix(Integer.parseInt(sCidr[1]));
				enableSpec.setServiceCidr(serviceCidr);
				MasterManagementNetwork masterManagementNetwork = new MasterManagementNetwork();
				masterManagementNetwork.setNetwork(getNetworkMOID(vkcBean.getNetworkName()));
				masterManagementNetwork.setMode(vkcBean.getMode());
				if (vkcBean.getMode().equals("STATICRANGE")) {
					AddressRange addressRange = new AddressRange();
					addressRange.setStartingAddress(vkcBean.getStartingAddress());
					addressRange.setAddressCount(vkcBean.getAddressCount());
					addressRange.setSubnetMask(vkcBean.getSubnetMask());
					addressRange.setGateway(vkcBean.getGateway());
					masterManagementNetwork.setAddressRange(addressRange);
				} else {
					masterManagementNetwork.setFloating_IP(vkcBean.getFloatingIP());
				}
				enableSpec.setMasterManagementNetwork(masterManagementNetwork);
				enableSpec.setMasterDNS(vkcBean.getMasterDNS());
				enableSpec.setMasterDNSSearchDomains(vkcBean.getMasterDNSSearchDomains());
				enableSpec.setMasterNTPServers(vkcBean.getMasterNTPServers());
				enableSpec.setWorkerDNS(vkcBean.getWorkerDNS());
				String storagePolicyID = getStoragePolicyID(vkcBean.getStoragePolicy());
				enableSpec.setMasterStoragePolicy(storagePolicyID);
				enableSpec.setEphemeralStoragePolicy(storagePolicyID);
				ImageStorage imageStorage = new ImageStorage();
				imageStorage.setStoragePolicy(storagePolicyID);
				enableSpec.setImageStorage(imageStorage);
				NcpClusterNetworkSpec ncpClusterNetworkSpec = new NcpClusterNetworkSpec();
				PodCidr[] podCidrs = new PodCidr[vkcBean.getPodCidrs().length];
				for (int i = 0; i < podCidrs.length; i++) {
					String[] podCidrSplit = vkcBean.getPodCidrs()[i].split("/");
					PodCidr podCidr = new PodCidr();
					podCidr.setAddress(podCidrSplit[0]);
					podCidr.setPrefix(Integer.parseInt(podCidrSplit[1]));
					podCidrs[i] = podCidr;
				}
				ncpClusterNetworkSpec.setPodCidrs(podCidrs);
				IngressCidr[] ingressCidrs = new IngressCidr[vkcBean.getIngressCidrs().length];
				for (int i = 0; i < ingressCidrs.length; i++) {
					String[] ingressCidrSplit = vkcBean.getIngressCidrs()[i].split("/");
					IngressCidr ingressCidr = new IngressCidr();
					ingressCidr.setAddress(ingressCidrSplit[0]);
					ingressCidr.setPrefix(Integer.parseInt(ingressCidrSplit[1]));
					ingressCidrs[i] = ingressCidr;
				}
				ncpClusterNetworkSpec.setIngressCidrs(ingressCidrs);
				EgressCidr[] egressCidrs = new EgressCidr[vkcBean.getEgressCidrs().length];
				for (int i = 0; i < ingressCidrs.length; i++) {
					String[] egressCidrSplit = vkcBean.getEgressCidrs()[i].split("/");
					EgressCidr egressCidr = new EgressCidr();
					egressCidr.setAddress(egressCidrSplit[0]);
					egressCidr.setPrefix(Integer.parseInt(egressCidrSplit[1]));
					egressCidrs[i] = egressCidr;
				}
				ncpClusterNetworkSpec.setEgressCidrs(egressCidrs);
				boolean isDvsEdgeComptible = false;
				DistributedSwitchCompatibility[] dvsCompatibleList = getCompatibleDistributedSwitch(clusterMoid);
				if (dvsCompatibleList != null) {
					for (DistributedSwitchCompatibility distributedSwitchCompatibility : dvsCompatibleList) {
						if (distributedSwitchCompatibility != null && distributedSwitchCompatibility.isCompatible()) {
							String distributedSwitchUUID = distributedSwitchCompatibility.getDistributed_switch();
							System.out.println("Compatible DistributedSwitch::" + distributedSwitchUUID);
							EdgeclusterCompatibility edgeClusterCompatible = getCompatibleEdgeCluster(clusterMoid,
									distributedSwitchUUID, vkcBean.getNsxEdgeClusterName());
							if (edgeClusterCompatible != null && edgeClusterCompatible.isCompatible()) {
								ncpClusterNetworkSpec.setClusterDistributedSwitch(distributedSwitchUUID);
								System.out
										.println("Compatible EdgeCluster::" + edgeClusterCompatible.getEdge_cluster());
								ncpClusterNetworkSpec.setNsxEdgeCluster(edgeClusterCompatible.getEdge_cluster());
								isDvsEdgeComptible = true;
								break;
							}
						}
					}
				}
				if (isDvsEdgeComptible) {
					enableSpec.setNcpClusterNetworkSpec(ncpClusterNetworkSpec);
					enableSpec.setNetworkProvider("NSXT_CONTAINER_PLUGIN");
					String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(enableSpec);
					System.out.println("Vsphere Kubernetes Cluster input json...\n" + jsonStr);
					enableVsphereKubernetesClusterAPI(enableSpec, clusterMoid);
				} else {
					getClusterCompatibility(clusterMoid);
					System.err.println("***Error::No Compatible DistributedSwitch/EdgeCluster in the vSphere Cluster::"
							+ vkcBean.getClusterName());
				}
			} else {
				System.err.println("***Error::Cluster is not compatible to enable vSphere Kubernetes cluster::"
						+ vkcBean.getClusterName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// vCenter credentials
		String vcip = "10.0.0.10";
		String vcUsername = "Administrator@vsphere.local";
		String vcPassword = "ndjkndsknsk";

		// Cluster name
		String clusterName = "Cluster1";
		// Master size
		String sizeHint = "TINY"; // TINY, SMALL, MEDIUM, LARGE

		// Management Network
		String networkName = "VM Network";
		String mode = "STATICRANGE"; // STATICRANGE or DHCP

		// Optional: Set floatingIP, if mode is DHCP otherwise set to null
		String floatingIP = null;

		// Optional: Set Addressrange, if mode is STATICRANGE
		String startingAddress = "10.0.0.50";
		int addressCount = 5;
		String subnetMask = "255.255.255.0";
		String gateway = "10.0.0.253";

		// Master DNS & NTP
		String masterDNS[] = { "10.0.0.100" };
		String masterNTPServers[] = { "10.0.0.102" };
		String masterDNSSearchDomains[] = { "vsphere.local" };

		// Workload Network
		String nsxEdgeClusterName = null; // Optional, it automatically selects
											// compatible edgecluster from the
											// compatible VDS.
		String workerDNS[] = { "10.0.0.101" };
		String podCidrs[] = { "10.244.0.0/21" };
		String serviceCidr = "10.96.0.0/24";
		String ingressCidrs[] = { "10.100.10.0/24" };
		String egressCidrs[] = { "10.100.20.0/24" };

		// VM Storage Policy name
		String storagePolicy = "storagepolicy1";

		VsphereKubernetesClusterBean clusterBean = new VsphereKubernetesClusterBean(clusterName, sizeHint, networkName,
				mode, floatingIP, startingAddress, addressCount, subnetMask, gateway, masterDNS, masterDNSSearchDomains,
				masterNTPServers, nsxEdgeClusterName, workerDNS, podCidrs, serviceCidr, ingressCidrs, egressCidrs,
				storagePolicy);
		new App(vcip, vcUsername, vcPassword).enableVsphereKubernetesCluster(clusterBean);
	}
}
