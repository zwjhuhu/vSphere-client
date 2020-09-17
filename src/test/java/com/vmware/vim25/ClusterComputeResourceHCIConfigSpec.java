
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHCIConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceHCIConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="dvsProf" type="{urn:vim25}ClusterComputeResourceDvsProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hostConfigProfile" type="{urn:vim25}ClusterComputeResourceHostConfigurationProfile" minOccurs="0"/>
 *         &lt;element name="vSanConfigSpec" type="{urn:vim25}SDDCBase" minOccurs="0"/>
 *         &lt;element name="vcProf" type="{urn:vim25}ClusterComputeResourceVCProfile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceHCIConfigSpec", propOrder = {
    "dvsProf",
    "hostConfigProfile",
    "vSanConfigSpec",
    "vcProf"
})
public class ClusterComputeResourceHCIConfigSpec
    extends DynamicData
{

    protected List<ClusterComputeResourceDvsProfile> dvsProf;
    protected ClusterComputeResourceHostConfigurationProfile hostConfigProfile;
    protected SDDCBase vSanConfigSpec;
    protected ClusterComputeResourceVCProfile vcProf;

    /**
     * Gets the value of the dvsProf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsProf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsProf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceDvsProfile }
     * 
     * 
     */
    public List<ClusterComputeResourceDvsProfile> getDvsProf() {
        if (dvsProf == null) {
            dvsProf = new ArrayList<ClusterComputeResourceDvsProfile>();
        }
        return this.dvsProf;
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

    /**
     * Gets the value of the vcProf property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterComputeResourceVCProfile }
     *     
     */
    public ClusterComputeResourceVCProfile getVcProf() {
        return vcProf;
    }

    /**
     * Sets the value of the vcProf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterComputeResourceVCProfile }
     *     
     */
    public void setVcProf(ClusterComputeResourceVCProfile value) {
        this.vcProf = value;
    }

}
