package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public class ConsumptionOpts {

    // used to compute derived attributes for location
    private Map<Long, String> regionNames;
    private Map<Long, String> regionCodes;

    private Map<Long, String> departmentNames;

    public Map<Long, String> getRegionNames() {
        return regionNames;
    }

    public void setRegionNames(Map<Long, String> regionNames) {
        this.regionNames = regionNames;
    }

    public Map<Long, String> getRegionCodes() {
        return regionCodes;
    }

    public void setRegionCodes(Map<Long, String> regionCodes) {
        this.regionCodes = regionCodes;
    }

    public Map<Long, String> getDepartmentNames() {
        return departmentNames;
    }

    public void setDepartmentNames(Map<Long, String> departmentNames) {
        this.departmentNames = departmentNames;
    }
}
