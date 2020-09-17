
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClusterComputeResourceValidationResultBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClusterComputeResourceValidationResultBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClusterComputeResourceValidationResultBase" type="{urn:vim25}ClusterComputeResourceValidationResultBase" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClusterComputeResourceValidationResultBase", propOrder = {
    "clusterComputeResourceValidationResultBase"
})
public class ArrayOfClusterComputeResourceValidationResultBase {

    @XmlElement(name = "ClusterComputeResourceValidationResultBase")
    protected List<ClusterComputeResourceValidationResultBase> clusterComputeResourceValidationResultBase;

    /**
     * Gets the value of the clusterComputeResourceValidationResultBase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusterComputeResourceValidationResultBase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusterComputeResourceValidationResultBase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceValidationResultBase }
     * 
     * 
     */
    public List<ClusterComputeResourceValidationResultBase> getClusterComputeResourceValidationResultBase() {
        if (clusterComputeResourceValidationResultBase == null) {
            clusterComputeResourceValidationResultBase = new ArrayList<ClusterComputeResourceValidationResultBase>();
        }
        return this.clusterComputeResourceValidationResultBase;
    }

}
