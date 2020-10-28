package org.devgateway.toolkit.persistence.dto.rainfallMap;

import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;

import java.util.SortedSet;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapConfig {

    private final SortedSet<Integer> years;

    private final RainfallMapLayerType[] layerTypes;

    public RainMapConfig(SortedSet<Integer> years) {
        this.years = years;
        this.layerTypes = RainfallMapLayerType.values();
    }

    public SortedSet<Integer> getYears() {
        return years;
    }

    public RainfallMapLayerType[] getLayerTypes() {
        return layerTypes;
    }
}
