package org.devgateway.toolkit.persistence.dto.rainfallMap;

import java.util.SortedSet;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapConfig {

    private final SortedSet<Integer> years;

    public RainMapConfig(SortedSet<Integer> years) {
        this.years = years;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
