package org.devgateway.toolkit.persistence.dto.season;

import org.devgateway.toolkit.persistence.dto.ChartConfig;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChartConfig extends ChartConfig {

    /**
     * Years for which there is at least some data available.
     */
    private final SortedSet<Integer> years;

    public SeasonChartConfig(String organization, Collection<Integer> years) {
        super(organization);
        this.years = new TreeSet<>(years);
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
