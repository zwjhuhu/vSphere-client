
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FolderDesiredHostState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FolderDesiredHostState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="maintenance"/>
 *     &lt;enumeration value="non_maintenance"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FolderDesiredHostState")
@XmlEnum
public enum FolderDesiredHostState {

    @XmlEnumValue("maintenance")
    MAINTENANCE("maintenance"),
    @XmlEnumValue("non_maintenance")
    NON_MAINTENANCE("non_maintenance");
    private final String value;

    FolderDesiredHostState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FolderDesiredHostState fromValue(String v) {
        for (FolderDesiredHostState c: FolderDesiredHostState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
