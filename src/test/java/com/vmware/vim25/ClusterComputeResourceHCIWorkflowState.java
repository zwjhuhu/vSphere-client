
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterComputeResourceHCIWorkflowState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClusterComputeResourceHCIWorkflowState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="in_progress"/>
 *     &lt;enumeration value="done"/>
 *     &lt;enumeration value="invalid"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClusterComputeResourceHCIWorkflowState")
@XmlEnum
public enum ClusterComputeResourceHCIWorkflowState {

    @XmlEnumValue("in_progress")
    IN_PROGRESS("in_progress"),
    @XmlEnumValue("done")
    DONE("done"),
    @XmlEnumValue("invalid")
    INVALID("invalid");
    private final String value;

    ClusterComputeResourceHCIWorkflowState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClusterComputeResourceHCIWorkflowState fromValue(String v) {
        for (ClusterComputeResourceHCIWorkflowState c: ClusterComputeResourceHCIWorkflowState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
