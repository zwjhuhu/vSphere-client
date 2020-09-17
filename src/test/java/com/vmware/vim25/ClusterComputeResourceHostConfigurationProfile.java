
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHostConfigurationProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHostConfigurationProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="dateTimeConfig" type="{urn:vim25}HostDateTimeConfig" minOccurs="0"/>
 *         &lt;element name="lockdownMode" type="{urn:vim25}HostLockdownMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceHostConfigurationProfile", propOrder = {
    "dateTimeConfig",
    "lockdownMode"
})
public class ClusterComputeResourceHostConfigurationProfile
    extends DynamicData
{

    protected HostDateTimeConfig dateTimeConfig;
    protected HostLockdownMode lockdownMode;

    /**
     * Gets the value of the dateTimeConfig property.
     * 
     * @return
     *     possible object is
     *     {@link HostDateTimeConfig }
     *     
     */
    public HostDateTimeConfig getDateTimeConfig() {
        return dateTimeConfig;
    }

    /**
     * Sets the value of the dateTimeConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostDateTimeConfig }
     *     
     */
    public void setDateTimeConfig(HostDateTimeConfig value) {
        this.dateTimeConfig = value;
    }

    /**
     * Gets the value of the lockdownMode property.
     * 
     * @return
     *     possible object is
     *     {@link HostLockdownMode }
     *     
     */
    public HostLockdownMode getLockdownMode() {
        return lockdownMode;
    }

    /**
     * Sets the value of the lockdownMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostLockdownMode }
     *     
     */
    public void setLockdownMode(HostLockdownMode value) {
        this.lockdownMode = value;
    }

}
