
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchAddStandaloneHostsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchAddStandaloneHostsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="newHosts" type="{urn:vim25}FolderNewHostSpec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="compResSpec" type="{urn:vim25}ComputeResourceConfigSpec" minOccurs="0"/>
 *         &lt;element name="addConnected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchAddStandaloneHostsRequestType", propOrder = {
    "_this",
    "newHosts",
    "compResSpec",
    "addConnected"
})
public class BatchAddStandaloneHostsRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    protected List<FolderNewHostSpec> newHosts;
    protected ComputeResourceConfigSpec compResSpec;
    protected boolean addConnected;

    /**
     * Gets the value of the this property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getThis() {
        return _this;
    }

    /**
     * Sets the value of the this property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setThis(ManagedObjectReference value) {
        this._this = value;
    }

    /**
     * Gets the value of the newHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the newHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNewHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FolderNewHostSpec }
     * 
     * 
     */
    public List<FolderNewHostSpec> getNewHosts() {
        if (newHosts == null) {
            newHosts = new ArrayList<FolderNewHostSpec>();
        }
        return this.newHosts;
    }

    /**
     * Gets the value of the compResSpec property.
     * 
     * @return
     *     possible object is
     *     {@link ComputeResourceConfigSpec }
     *     
     */
    public ComputeResourceConfigSpec getCompResSpec() {
        return compResSpec;
    }

    /**
     * Sets the value of the compResSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComputeResourceConfigSpec }
     *     
     */
    public void setCompResSpec(ComputeResourceConfigSpec value) {
        this.compResSpec = value;
    }

    /**
     * Gets the value of the addConnected property.
     * 
     */
    public boolean isAddConnected() {
        return addConnected;
    }

    /**
     * Sets the value of the addConnected property.
     * 
     */
    public void setAddConnected(boolean value) {
        this.addConnected = value;
    }

}
