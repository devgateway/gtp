package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;

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
    private final List<PluviometricPost> pluviometricPosts;

    public RainLevelChartConfig(List<Integer> years,
            List<PluviometricPost> pluviometricPosts) {
        this.years = new TreeSet<>(years);
        this.pluviometricPosts = pluviometricPosts;
    }

    public SortedSet<Integer> getYears() {
        return years;
    }

    public List<PluviometricPost> getPluviometricPosts() {
        return pluviometricPosts;
    }
}
