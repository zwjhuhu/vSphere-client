
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtendHCIRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtendHCIRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="hostInputs" type="{urn:vim25}ClusterComputeResourceHostConfigurationInput" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vSanConfigSpec" type="{urn:vim25}SDDCBase" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtendHCIRequestType", propOrder = {
    "_this",
    "hostInputs",
    "vSanConfigSpec"
})
public class ExtendHCIRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    protected List<ClusterComputeResourceHostConfigurationInput> hostInputs;
    protected SDDCBase vSanConfigSpec;

    /**
     * Gets the value of the this property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getThis() {
        return _this;
    }

    /**
     * Sets the value of the this property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setThis(ManagedObjectReference value) {
        this._this = value;
    }

    /**
     * Gets the value of the hostInputs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostInputs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostInputs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceHostConfigurationInput }
     * 
     * 
     */
    public List<ClusterComputeResourceHostConfigurationInput> getHostInputs() {
        if (hostInputs == null) {
            hostInputs = new ArrayList<ClusterComputeResourceHostConfigurationInput>();
        }
        return this.hostInputs;
    }

    /**
     * Gets the value of the vSanConfigSpec property.
     * 
     * @return
     *     possible object is
     *     {@link SDDCBase }
     *     
     */
    public SDDCBase getVSanConfigSpec() {
        return vSanConfigSpec;
    }

    /**
     * Sets the value of the vSanConfigSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDDCBase }
     *     
     */
    public void setVSanConfigSpec(SDDCBase value) {
        this.vSanConfigSpec = value;
    }

}
