
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceDVSConfigurationValidation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterComputeResourceDVSConfigurationValidation">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}ClusterComputeResourceValidationResultBase">
 *       &lt;sequence>
 *         &lt;element name="isDvsValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isDvpgValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterComputeResourceDVSConfigurationValidation", propOrder = {
    "isDvsValid",
    "isDvpgValid"
})
public class ClusterComputeResourceDVSConfigurationValidation
    extends ClusterComputeResourceValidationResultBase
{

    protected boolean isDvsValid;
    protected boolean isDvpgValid;

    /**
     * Gets the value of the isDvsValid property.
     * 
     */
    public boolean isIsDvsValid() {
        return isDvsValid;
    }

    /**
     * Sets the value of the isDvsValid property.
     * 
     */
    public void setIsDvsValid(boolean value) {
        this.isDvsValid = value;
    }

    /**
     * Gets the value of the isDvpgValid property.
     * 
     */
    public boolean isIsDvpgValid() {
        return isDvpgValid;
    }

    /**
     * Sets the value of the isDvpgValid property.
     * 
     */
    public void setIsDvpgValid(boolean value) {
        this.isDvpgValid = value;
    }

}
