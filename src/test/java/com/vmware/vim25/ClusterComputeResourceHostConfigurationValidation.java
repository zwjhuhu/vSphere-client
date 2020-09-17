
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHostConfigurationValidation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHostConfigurationValidation">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}ClusterComputeResourceValidationResultBase">
 *       &lt;sequence>
 *         &lt;element name="host" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="isDvsSettingValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isVmknicSettingValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isNtpSettingValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isLockdownModeValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceHostConfigurationValidation", propOrder = {
    "host",
    "isDvsSettingValid",
    "isVmknicSettingValid",
    "isNtpSettingValid",
    "isLockdownModeValid"
})
public class ClusterComputeResourceHostConfigurationValidation
    extends ClusterComputeResourceValidationResultBase
{

    @XmlElement(required = true)
    protected ManagedObjectReference host;
    protected Boolean isDvsSettingValid;
    protected Boolean isVmknicSettingValid;
    protected Boolean isNtpSettingValid;
    protected Boolean isLockdownModeValid;

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
     * Gets the value of the isDvsSettingValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDvsSettingValid() {
        return isDvsSettingValid;
    }

    /**
     * Sets the value of the isDvsSettingValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDvsSettingValid(Boolean value) {
        this.isDvsSettingValid = value;
    }

    /**
     * Gets the value of the isVmknicSettingValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVmknicSettingValid() {
        return isVmknicSettingValid;
    }

    /**
     * Sets the value of the isVmknicSettingValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVmknicSettingValid(Boolean value) {
        this.isVmknicSettingValid = value;
    }

    /**
     * Gets the value of the isNtpSettingValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNtpSettingValid() {
        return isNtpSettingValid;
    }

    /**
     * Sets the value of the isNtpSettingValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNtpSettingValid(Boolean value) {
        this.isNtpSettingValid = value;
    }

    /**
     * Gets the value of the isLockdownModeValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLockdownModeValid() {
        return isLockdownModeValid;
    }

    /**
     * Sets the value of the isLockdownModeValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLockdownModeValid(Boolean value) {
        this.isLockdownModeValid = value;
    }

}
