package org.devgateway.toolkit.persistence.dto.livestock.disease;

import org.devgateway.toolkit.persistence.dto.ChartConfig;

import java.util.SortedSet;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityConfig extends ChartConfig {

    private final SortedSet<Integer> years;

    public DiseaseQuantityConfig(String organization, SortedSet<Integer> years) {
        super(organization);
        this.years = years;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
