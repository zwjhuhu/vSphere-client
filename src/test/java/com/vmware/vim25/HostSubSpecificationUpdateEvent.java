
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostSubSpecificationUpdateEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostSubSpecificationUpdateEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostEvent">
 *       &lt;sequence>
 *         &lt;element name="hostSubSpec" type="{urn:vim25}HostSubSpecification"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostSubSpecificationUpdateEvent", propOrder = {
    "hostSubSpec"
})
public class HostSubSpecificationUpdateEvent
    extends HostEvent
{

    @XmlElement(required = true)
    protected HostSubSpecification hostSubSpec;

    /**
     * Gets the value of the hostSubSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostSubSpecification }
     *     
     */
    public HostSubSpecification getHostSubSpec() {
        return hostSubSpec;
    }

    /**
     * Sets the value of the hostSubSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostSubSpecification }
     *     
     */
    public void setHostSubSpec(HostSubSpecification value) {
        this.hostSubSpec = value;
    }

}
