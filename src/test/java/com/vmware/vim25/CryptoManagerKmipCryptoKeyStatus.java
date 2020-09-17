
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CryptoManagerKmipCryptoKeyStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CryptoManagerKmipCryptoKeyStatus">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="keyId" type="{urn:vim25}CryptoKeyId"/>
 *         &lt;element name="keyAvailable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encryptedVMs" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="affectedHosts" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="referencedByTags" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CryptoManagerKmipCryptoKeyStatus", propOrder = {
    "keyId",
    "keyAvailable",
    "reason",
    "encryptedVMs",
    "affectedHosts",
    "referencedByTags"
})
public class CryptoManagerKmipCryptoKeyStatus
    extends DynamicData
{

    @XmlElement(required = true)
    protected CryptoKeyId keyId;
    protected Boolean keyAvailable;
    protected String reason;
    protected List<ManagedObjectReference> encryptedVMs;
    protected List<ManagedObjectReference> affectedHosts;
    protected List<String> referencedByTags;

    /**
     * Gets the value of the keyId property.
     * 
     * @return
     *     possible object is
     *     {@link CryptoKeyId }
     *     
     */
    public CryptoKeyId getKeyId() {
        return keyId;
    }

    /**
     * Sets the value of the keyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CryptoKeyId }
     *     
     */
    public void setKeyId(CryptoKeyId value) {
        this.keyId = value;
    }

    /**
     * Gets the value of the keyAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeyAvailable() {
        return keyAvailable;
    }

    /**
     * Sets the value of the keyAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeyAvailable(Boolean value) {
        this.keyAvailable = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the encryptedVMs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encryptedVMs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEncryptedVMs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getEncryptedVMs() {
        if (encryptedVMs == null) {
            encryptedVMs = new ArrayList<ManagedObjectReference>();
        }
        return this.encryptedVMs;
    }

    /**
     * Gets the value of the affectedHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the affectedHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAffectedHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getAffectedHosts() {
        if (affectedHosts == null) {
            affectedHosts = new ArrayList<ManagedObjectReference>();
        }
        return this.affectedHosts;
    }

    /**
     * Gets the value of the referencedByTags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referencedByTags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferencedByTags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReferencedByTags() {
        if (referencedByTags == null) {
            referencedByTags = new ArrayList<String>();
        }
        return this.referencedByTags;
    }

}
