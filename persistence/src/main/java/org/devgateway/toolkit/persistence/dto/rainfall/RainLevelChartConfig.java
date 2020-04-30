package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
public class RainLevelChartConfig {

    /**
     * Years for which there is at least some data available.
     */
    private final SortedSet<Integer> years;

    /**
     * Pluviometric posts for which there is at least some data available.
     */
    private final List<Long> pluviometricPostIds;

    public RainLevelChartConfig(List<Integer> years,
            List<Long> pluviometricPostIds) {
        this.years = new TreeSet<>(years);
        this.pluviometricPostIds = pluviometricPostIds;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }

    public List<Long> getPluviometricPostIds() {
        return pluviometricPostIds;
    }
}
