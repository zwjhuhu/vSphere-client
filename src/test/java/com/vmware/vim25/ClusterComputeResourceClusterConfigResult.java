
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceClusterConfigResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceClusterConfigResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="failedHosts" type="{urn:vim25}FolderFailedHostResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="configuredHosts" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceClusterConfigResult", propOrder = {
    "failedHosts",
    "configuredHosts"
})
public class ClusterComputeResourceClusterConfigResult
    extends DynamicData
{

    protected List<FolderFailedHostResult> failedHosts;
    protected List<ManagedObjectReference> configuredHosts;

    /**
     * Gets the value of the failedHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the failedHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFailedHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FolderFailedHostResult }
     * 
     * 
     */
    public List<FolderFailedHostResult> getFailedHosts() {
        if (failedHosts == null) {
            failedHosts = new ArrayList<FolderFailedHostResult>();
        }
        return this.failedHosts;
    }

    /**
     * Gets the value of the configuredHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configuredHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfiguredHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getConfiguredHosts() {
        if (configuredHosts == null) {
            configuredHosts = new ArrayList<ManagedObjectReference>();
        }
        return this.configuredHosts;
    }

}
