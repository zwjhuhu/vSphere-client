
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="dvPortgroup" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="service" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping", propOrder = {
    "dvPortgroup",
    "service"
})
public class ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference dvPortgroup;
    @XmlElement(required = true)
    protected String service;

    /**
     * Gets the value of the dvPortgroup property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getDvPortgroup() {
        return dvPortgroup;
    }

    /**
     * Sets the value of the dvPortgroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setDvPortgroup(ManagedObjectReference value) {
        this.dvPortgroup = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

}
