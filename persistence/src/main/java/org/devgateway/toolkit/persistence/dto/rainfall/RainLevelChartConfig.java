package org.devgateway.toolkit.persistence.dto.rainfall;

import org.devgateway.toolkit.persistence.dto.ChartConfig;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
public class RainLevelChartConfig extends ChartConfig {

    /**
     * Years for which there is at least some data available.
     */
    private final SortedSet<Integer> years;

    /**
     * Pluviometric posts for which there is at least some data available.
     */
    private final List<Long> pluviometricPostIds;

    public RainLevelChartConfig(
            String organization,
            List<Integer> years,
            List<Long> pluviometricPostIds) {
        super(organization);
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
