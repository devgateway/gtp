package org.devgateway.toolkit.persistence.dto.season;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChartConfig {

    /**
     * Years for which there is at least some data available.
     */
    private final SortedSet<Integer> years;

    public SeasonChartConfig(Collection<Integer> years) {
        this.years = new TreeSet<>(years);
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
