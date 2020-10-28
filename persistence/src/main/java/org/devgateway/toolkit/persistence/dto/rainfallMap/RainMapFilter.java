package org.devgateway.toolkit.persistence.dto.rainfallMap;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;

import javax.validation.constraints.NotNull;
import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapFilter {

    @NotNull
    private final Integer year;

    @NotNull
    private final Month month;

    @NotNull
    private final Decadal decadal;

    @NotNull
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
