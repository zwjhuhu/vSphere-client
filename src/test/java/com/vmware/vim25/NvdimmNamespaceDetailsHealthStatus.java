
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NvdimmNamespaceDetailsHealthStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NvdimmNamespaceDetailsHealthStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="normal"/>
 *     &lt;enumeration value="missing"/>
 *     &lt;enumeration value="labelMissing"/>
 *     &lt;enumeration value="interleaveBroken"/>
 *     &lt;enumeration value="labelInconsistent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NvdimmNamespaceDetailsHealthStatus")
@XmlEnum
public enum NvdimmNamespaceDetailsHealthStatus {

    @XmlEnumValue("normal")
    NORMAL("normal"),
    @XmlEnumValue("missing")
    MISSING("missing"),
    @XmlEnumValue("labelMissing")
    LABEL_MISSING("labelMissing"),
    @XmlEnumValue("interleaveBroken")
    INTERLEAVE_BROKEN("interleaveBroken"),
    @XmlEnumValue("labelInconsistent")
    LABEL_INCONSISTENT("labelInconsistent");
    private final String value;

    NvdimmNamespaceDetailsHealthStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NvdimmNamespaceDetailsHealthStatus fromValue(String v) {
        for (NvdimmNamespaceDetailsHealthStatus c: NvdimmNamespaceDetailsHealthStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
