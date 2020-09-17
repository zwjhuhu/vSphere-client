
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="KeyStateMissingInCache"/>
 *     &lt;enumeration value="KeyStateClusterInvalid"/>
 *     &lt;enumeration value="KeyStateClusterUnreachable"/>
 *     &lt;enumeration value="KeyStateMissingInKMS"/>
 *     &lt;enumeration value="KeyStateNotActiveOrEnabled"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason")
@XmlEnum
public enum CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason {

    @XmlEnumValue("KeyStateMissingInCache")
    KEY_STATE_MISSING_IN_CACHE("KeyStateMissingInCache"),
    @XmlEnumValue("KeyStateClusterInvalid")
    KEY_STATE_CLUSTER_INVALID("KeyStateClusterInvalid"),
    @XmlEnumValue("KeyStateClusterUnreachable")
    KEY_STATE_CLUSTER_UNREACHABLE("KeyStateClusterUnreachable"),
    @XmlEnumValue("KeyStateMissingInKMS")
    KEY_STATE_MISSING_IN_KMS("KeyStateMissingInKMS"),
    @XmlEnumValue("KeyStateNotActiveOrEnabled")
    KEY_STATE_NOT_ACTIVE_OR_ENABLED("KeyStateNotActiveOrEnabled");
    private final String value;

    CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason fromValue(String v) {
        for (CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason c: CryptoManagerKmipCryptoKeyStatusKeyUnavailableReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
