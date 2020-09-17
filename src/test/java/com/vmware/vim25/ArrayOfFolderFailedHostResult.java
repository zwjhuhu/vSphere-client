
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFolderFailedHostResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFolderFailedHostResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FolderFailedHostResult" type="{urn:vim25}FolderFailedHostResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFolderFailedHostResult", propOrder = {
    "folderFailedHostResult"
})
public class ArrayOfFolderFailedHostResult {

    @XmlElement(name = "FolderFailedHostResult")
    protected List<FolderFailedHostResult> folderFailedHostResult;

    /**
     * Gets the value of the folderFailedHostResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the folderFailedHostResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFolderFailedHostResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FolderFailedHostResult }
     * 
     * 
     */
    public List<FolderFailedHostResult> getFolderFailedHostResult() {
        if (folderFailedHostResult == null) {
            folderFailedHostResult = new ArrayList<FolderFailedHostResult>();
        }
        return this.folderFailedHostResult;
    }

}
