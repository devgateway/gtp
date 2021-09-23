package org.devgateway.toolkit.persistence.dto.agriculture;

import org.devgateway.toolkit.persistence.dto.ChartConfig;

import java.util.SortedSet;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPricesChartConfig extends ChartConfig {

    private final SortedSet<Integer> years;

    public ProductPricesChartConfig(String organization, SortedSet<Integer> years) {
        super(organization);
        this.years = years;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
