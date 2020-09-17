
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceDVSSetting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceDVSSetting">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="dvSwitch" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="pnicDevices" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dvPortgroupSetting" type="{urn:vim25}ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceDVSSetting", propOrder = {
    "dvSwitch",
    "pnicDevices",
    "dvPortgroupSetting"
})
public class ClusterComputeResourceDVSSetting
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference dvSwitch;
    protected List<String> pnicDevices;
    protected List<ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping> dvPortgroupSetting;

    /**
     * Gets the value of the dvSwitch property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getDvSwitch() {
        return dvSwitch;
    }

    /**
     * Sets the value of the dvSwitch property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setDvSwitch(ManagedObjectReference value) {
        this.dvSwitch = value;
    }

    /**
     * Gets the value of the pnicDevices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pnicDevices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPnicDevices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPnicDevices() {
        if (pnicDevices == null) {
            pnicDevices = new ArrayList<String>();
        }
        return this.pnicDevices;
    }

    /**
     * Gets the value of the dvPortgroupSetting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvPortgroupSetting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvPortgroupSetting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping }
     * 
     * 
     */
    public List<ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping> getDvPortgroupSetting() {
        if (dvPortgroupSetting == null) {
            dvPortgroupSetting = new ArrayList<ClusterComputeResourceDVSSettingDVPortgroupToServiceMapping>();
        }
        return this.dvPortgroupSetting;
    }

}
