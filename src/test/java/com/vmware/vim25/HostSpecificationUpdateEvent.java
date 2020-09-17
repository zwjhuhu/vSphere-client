
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostSpecificationUpdateEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostSpecificationUpdateEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostEvent">
 *       &lt;sequence>
 *         &lt;element name="hostSpec" type="{urn:vim25}HostSpecification"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostSpecificationUpdateEvent", propOrder = {
    "hostSpec"
})
public class HostSpecificationUpdateEvent
    extends HostEvent
{

    @XmlElement(required = true)
    protected HostSpecification hostSpec;

    /**
     * Gets the value of the hostSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostSpecification }
     *     
     */
    public HostSpecification getHostSpec() {
        return hostSpec;
    }

    /**
     * Sets the value of the hostSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostSpecification }
     *     
     */
    public void setHostSpec(HostSpecification value) {
        this.hostSpec = value;
    }

}
