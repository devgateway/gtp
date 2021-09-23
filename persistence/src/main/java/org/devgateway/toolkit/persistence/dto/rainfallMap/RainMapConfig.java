package org.devgateway.toolkit.persistence.dto.rainfallMap;

import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;
import org.devgateway.toolkit.persistence.dto.ChartConfig;

import java.util.SortedSet;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapConfig extends ChartConfig {

    private final SortedSet<Integer> years;

    private final RainfallMapLayerType[] layerTypes;

    public RainMapConfig(String organization, SortedSet<Integer> years) {
        super(organization);
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
