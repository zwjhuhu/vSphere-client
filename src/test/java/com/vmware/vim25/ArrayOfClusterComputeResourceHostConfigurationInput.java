
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClusterComputeResourceHostConfigurationInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClusterComputeResourceHostConfigurationInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClusterComputeResourceHostConfigurationInput" type="{urn:vim25}ClusterComputeResourceHostConfigurationInput" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClusterComputeResourceHostConfigurationInput", propOrder = {
    "clusterComputeResourceHostConfigurationInput"
})
public class ArrayOfClusterComputeResourceHostConfigurationInput {

    @XmlElement(name = "ClusterComputeResourceHostConfigurationInput")
    protected List<ClusterComputeResourceHostConfigurationInput> clusterComputeResourceHostConfigurationInput;

    /**
     * Gets the value of the clusterComputeResourceHostConfigurationInput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusterComputeResourceHostConfigurationInput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusterComputeResourceHostConfigurationInput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterComputeResourceHostConfigurationInput }
     * 
     * 
     */
    public List<ClusterComputeResourceHostConfigurationInput> getClusterComputeResourceHostConfigurationInput() {
        if (clusterComputeResourceHostConfigurationInput == null) {
            clusterComputeResourceHostConfigurationInput = new ArrayList<ClusterComputeResourceHostConfigurationInput>();
        }
        return this.clusterComputeResourceHostConfigurationInput;
    }

}
