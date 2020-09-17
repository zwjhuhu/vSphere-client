
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FolderNewHostSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FolderNewHostSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="hostCnxSpec" type="{urn:vim25}HostConnectSpec"/>
 *         &lt;element name="esxLicense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderNewHostSpec", propOrder = {
    "hostCnxSpec",
    "esxLicense"
})
public class FolderNewHostSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected HostConnectSpec hostCnxSpec;
    protected String esxLicense;

    /**
     * Gets the value of the hostCnxSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostConnectSpec }
     *     
     */
    public HostConnectSpec getHostCnxSpec() {
        return hostCnxSpec;
    }

    /**
     * Sets the value of the hostCnxSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostConnectSpec }
     *     
     */
    public void setHostCnxSpec(HostConnectSpec value) {
        this.hostCnxSpec = value;
    }

    /**
     * Gets the value of the esxLicense property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsxLicense() {
        return esxLicense;
    }

    /**
     * Sets the value of the esxLicense property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsxLicense(String value) {
        this.esxLicense = value;
    }

}
