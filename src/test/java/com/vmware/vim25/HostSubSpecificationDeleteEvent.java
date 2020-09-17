
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostSubSpecificationDeleteEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostSubSpecificationDeleteEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostEvent">
 *       &lt;sequence>
 *         &lt;element name="subSpecName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostSubSpecificationDeleteEvent", propOrder = {
    "subSpecName"
})
public class HostSubSpecificationDeleteEvent
    extends HostEvent
{

    @XmlElement(required = true)
    protected String subSpecName;

    /**
     * Gets the value of the subSpecName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubSpecName() {
        return subSpecName;
    }

    /**
     * Sets the value of the subSpecName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubSpecName(String value) {
        this.subSpecName = value;
    }

}
