package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public class ProductionOpts {

    // used to compute derived attributes for location
    private Map<Long, String> regionNames;
    private Map<Long, String> regionCodes;

    // used to compute derived attribute crop type
    private Map<Long, String> cropTypeNames;

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

    public Map<Long, String> getCropTypeNames() {
        return cropTypeNames;
    }

    public void setCropTypeNames(Map<Long, String> cropTypeNames) {
        this.cropTypeNames = cropTypeNames;
    }
}
