package org.devgateway.toolkit.persistence.dto.livestock.disease;

import java.util.SortedSet;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityConfig {

    private final SortedSet<Integer> years;

    public DiseaseQuantityConfig(SortedSet<Integer> years) {
        this.years = years;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }
}
