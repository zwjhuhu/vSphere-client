
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHostVmkNicInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHostVmkNicInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="nicSpec" type="{urn:vim25}HostVirtualNicSpec"/>
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
@XmlType(name = "ClusterComputeResourceHostVmkNicInfo", propOrder = {
    "nicSpec",
    "service"
})
public class ClusterComputeResourceHostVmkNicInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected HostVirtualNicSpec nicSpec;
    @XmlElement(required = true)
    protected String service;

    /**
     * Gets the value of the nicSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostVirtualNicSpec }
     *     
     */
    public HostVirtualNicSpec getNicSpec() {
        return nicSpec;
    }

    /**
     * Sets the value of the nicSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVirtualNicSpec }
     *     
     */
    public void setNicSpec(HostVirtualNicSpec value) {
        this.nicSpec = value;
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
