package org.devgateway.toolkit.persistence.dto.rainfallMap;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;

import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapFilter {

    private final Integer year;

    private final Month month;

    private final Decadal decadal;

    private final RainfallMapLayerType layerType;

    public RainMapFilter(Integer year, Month month, Decadal decadal, RainfallMapLayerType layerType) {
        this.year = year;
        this.month = month;
        this.decadal = decadal;
        this.layerType = layerType;
    }

    public Integer getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public RainfallMapLayerType getLayerType() {
        return layerType;
    }
}
