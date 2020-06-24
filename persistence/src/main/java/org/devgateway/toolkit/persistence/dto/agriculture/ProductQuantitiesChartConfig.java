package org.devgateway.toolkit.persistence.dto.agriculture;

import java.util.SortedSet;

/**
 * @author Octavian Ciubotaru
 */
public class ProductQuantitiesChartConfig {

    private final SortedSet<Integer> years;

    public ProductQuantitiesChartConfig(SortedSet<Integer> years) {
        this.years = years;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
