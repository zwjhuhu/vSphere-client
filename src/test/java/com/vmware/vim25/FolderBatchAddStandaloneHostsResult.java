
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FolderBatchAddStandaloneHostsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FolderBatchAddStandaloneHostsResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="addedHosts" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hostsFailedInventoryAdd" type="{urn:vim25}FolderFailedHostResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderBatchAddStandaloneHostsResult", propOrder = {
    "addedHosts",
    "hostsFailedInventoryAdd"
})
public class FolderBatchAddStandaloneHostsResult
    extends DynamicData
{

    protected List<ManagedObjectReference> addedHosts;
    protected List<FolderFailedHostResult> hostsFailedInventoryAdd;

    /**
     * Gets the value of the addedHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addedHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddedHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getAddedHosts() {
        if (addedHosts == null) {
            addedHosts = new ArrayList<ManagedObjectReference>();
        }
        return this.addedHosts;
    }

    /**
     * Gets the value of the hostsFailedInventoryAdd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostsFailedInventoryAdd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostsFailedInventoryAdd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FolderFailedHostResult }
     * 
     * 
     */
    public List<FolderFailedHostResult> getHostsFailedInventoryAdd() {
        if (hostsFailedInventoryAdd == null) {
            hostsFailedInventoryAdd = new ArrayList<FolderFailedHostResult>();
        }
        return this.hostsFailedInventoryAdd;
    }

}
