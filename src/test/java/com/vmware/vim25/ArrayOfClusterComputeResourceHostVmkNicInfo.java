
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClusterComputeResourceHostVmkNicInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClusterComputeResourceHostVmkNicInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClusterComputeResourceHostVmkNicInfo" type="{urn:vim25}ClusterComputeResourceHostVmkNicInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClusterComputeResourceHostVmkNicInfo", propOrder = {
    "clusterComputeResourceHostVmkNicInfo"
})
public class ArrayOfClusterComputeResourceHostVmkNicInfo {

    @XmlElement(name = "ClusterComputeResourceHostVmkNicInfo")
    protected List<ClusterComputeResourceHostVmkNicInfo> clusterComputeResourceHostVmkNicInfo;

    /**
     * Gets the value of the clusterComputeResourceHostVmkNicInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusterComputeResourceHostVmkNicInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusterComputeResourceHostVmkNicInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceHostVmkNicInfo }
     * 
     * 
     */
    public List<ClusterComputeResourceHostVmkNicInfo> getClusterComputeResourceHostVmkNicInfo() {
        if (clusterComputeResourceHostVmkNicInfo == null) {
            clusterComputeResourceHostVmkNicInfo = new ArrayList<ClusterComputeResourceHostVmkNicInfo>();
        }
        return this.clusterComputeResourceHostVmkNicInfo;
    }

}
