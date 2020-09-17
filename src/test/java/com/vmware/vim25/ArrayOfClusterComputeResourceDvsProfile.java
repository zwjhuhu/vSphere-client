
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClusterComputeResourceDvsProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClusterComputeResourceDvsProfile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClusterComputeResourceDvsProfile" type="{urn:vim25}ClusterComputeResourceDvsProfile" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClusterComputeResourceDvsProfile", propOrder = {
    "clusterComputeResourceDvsProfile"
})
public class ArrayOfClusterComputeResourceDvsProfile {

    @XmlElement(name = "ClusterComputeResourceDvsProfile")
    protected List<ClusterComputeResourceDvsProfile> clusterComputeResourceDvsProfile;

    /**
     * Gets the value of the clusterComputeResourceDvsProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusterComputeResourceDvsProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusterComputeResourceDvsProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceDvsProfile }
     * 
     * 
     */
    public List<ClusterComputeResourceDvsProfile> getClusterComputeResourceDvsProfile() {
        if (clusterComputeResourceDvsProfile == null) {
            clusterComputeResourceDvsProfile = new ArrayList<ClusterComputeResourceDvsProfile>();
        }
        return this.clusterComputeResourceDvsProfile;
    }

}
