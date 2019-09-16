package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.Map;

/**
 * Opts to pass to javascript that are related to {@link org.devgateway.toolkit.persistence.dao.MarketPrice}.
 *
 * @author Octavian Ciubotaru
 */
public class MarketPriceOpts {

    // used to compute derived attributes for location
    private Map<Long, String> regionNames;
    private Map<Long, String> regionCodes;
    private Map<Long, String> departmentNames;
    private Map<Long, String> marketNames;

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
}
