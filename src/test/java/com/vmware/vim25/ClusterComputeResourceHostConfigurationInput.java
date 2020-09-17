
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHostConfigurationInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHostConfigurationInput">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="host" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="hostVmkNics" type="{urn:vim25}ClusterComputeResourceHostVmkNicInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allowedInNonMaintenanceMode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceHostConfigurationInput", propOrder = {
    "host",
    "hostVmkNics",
    "allowedInNonMaintenanceMode"
})
public class ClusterComputeResourceHostConfigurationInput
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference host;
    protected List<ClusterComputeResourceHostVmkNicInfo> hostVmkNics;
    protected Boolean allowedInNonMaintenanceMode;

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setHost(ManagedObjectReference value) {
        this.host = value;
    }

    /**
     * Gets the value of the hostVmkNics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostVmkNics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostVmkNics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceHostVmkNicInfo }
     * 
     * 
     */
    public List<ClusterComputeResourceHostVmkNicInfo> getHostVmkNics() {
        if (hostVmkNics == null) {
            hostVmkNics = new ArrayList<ClusterComputeResourceHostVmkNicInfo>();
        }
        return this.hostVmkNics;
    }

    /**
     * Gets the value of the allowedInNonMaintenanceMode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowedInNonMaintenanceMode() {
        return allowedInNonMaintenanceMode;
    }

    /**
     * Sets the value of the allowedInNonMaintenanceMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowedInNonMaintenanceMode(Boolean value) {
        this.allowedInNonMaintenanceMode = value;
    }

}
