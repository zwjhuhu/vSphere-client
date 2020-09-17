
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHCIConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHCIConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="workflowState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dvsSetting" type="{urn:vim25}ClusterComputeResourceDVSSetting" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="configuredHosts" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hostConfigProfile" type="{urn:vim25}ClusterComputeResourceHostConfigurationProfile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceHCIConfigInfo", propOrder = {
    "workflowState",
    "dvsSetting",
    "configuredHosts",
    "hostConfigProfile"
})
public class ClusterComputeResourceHCIConfigInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected String workflowState;
    protected List<ClusterComputeResourceDVSSetting> dvsSetting;
    protected List<ManagedObjectReference> configuredHosts;
    protected ClusterComputeResourceHostConfigurationProfile hostConfigProfile;

    /**
     * Gets the value of the workflowState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowState() {
        return workflowState;
    }

    /**
     * Sets the value of the workflowState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowState(String value) {
        this.workflowState = value;
    }

    /**
     * Gets the value of the dvsSetting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsSetting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsSetting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceDVSSetting }
     * 
     * 
     */
    public List<ClusterComputeResourceDVSSetting> getDvsSetting() {
        if (dvsSetting == null) {
            dvsSetting = new ArrayList<ClusterComputeResourceDVSSetting>();
        }
        return this.dvsSetting;
    }

    /**
     * Gets the value of the configuredHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configuredHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfiguredHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getConfiguredHosts() {
        if (configuredHosts == null) {
            configuredHosts = new ArrayList<ManagedObjectReference>();
        }
        return this.configuredHosts;
    }

    /**
     * Gets the value of the hostConfigProfile property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterComputeResourceHostConfigurationProfile }
     *     
     */
    public ClusterComputeResourceHostConfigurationProfile getHostConfigProfile() {
        return hostConfigProfile;
    }

    /**
     * Sets the value of the hostConfigProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterComputeResourceHostConfigurationProfile }
     *     
     */
    public void setHostConfigProfile(ClusterComputeResourceHostConfigurationProfile value) {
        this.hostConfigProfile = value;
    }

}
