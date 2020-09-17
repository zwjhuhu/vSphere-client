
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceVCProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceVCProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="clusterSpec" type="{urn:vim25}ClusterConfigSpecEx" minOccurs="0"/>
 *         &lt;element name="evcModeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceVCProfile", propOrder = {
    "clusterSpec",
    "evcModeKey"
})
public class ClusterComputeResourceVCProfile
    extends DynamicData
{

    protected ClusterConfigSpecEx clusterSpec;
    protected String evcModeKey;

    /**
     * Gets the value of the clusterSpec property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterConfigSpecEx }
     *     
     */
    public ClusterConfigSpecEx getClusterSpec() {
        return clusterSpec;
    }

    /**
     * Sets the value of the clusterSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterConfigSpecEx }
     *     
     */
    public void setClusterSpec(ClusterConfigSpecEx value) {
        this.clusterSpec = value;
    }

    /**
     * Gets the value of the evcModeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvcModeKey() {
        return evcModeKey;
    }

    /**
     * Sets the value of the evcModeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvcModeKey(String value) {
        this.evcModeKey = value;
    }

}
