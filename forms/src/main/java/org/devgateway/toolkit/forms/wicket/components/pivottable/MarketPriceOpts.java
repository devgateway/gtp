package org.devgateway.toolkit.forms.wicket.components.pivottable;

import org.devgateway.toolkit.persistence.dao.ipar.MarketPrice;

import java.util.Map;

/**
 * Opts to pass to javascript that are related to {@link MarketPrice}.
 *
 * @author Octavian Ciubotaru
 */
public class MarketPriceOpts {

    // used to compute derived attributes for location
    private Map<Long, String> regionNames;
    private Map<Long, String> regionCodes;
    private Map<Long, String> departmentNames;
    private Map<Long, String> marketNames;

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

    public Map<Long, String> getDepartmentNames() {
        return departmentNames;
    }

    public void setDepartmentNames(Map<Long, String> departmentNames) {
        this.departmentNames = departmentNames;
    }

    public Map<Long, String> getMarketNames() {
        return marketNames;
    }

    public void setMarketNames(Map<Long, String> marketNames) {
        this.marketNames = marketNames;
    }

    public Map<Long, String> getCropTypeNames() {
        return cropTypeNames;
    }

    public void setCropTypeNames(Map<Long, String> cropTypeNames) {
        this.cropTypeNames = cropTypeNames;
    }
}
