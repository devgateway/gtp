package org.devgateway.toolkit.persistence.dto.riverlevel;

import java.util.List;
import java.util.SortedSet;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelChartConfig {

    private final SortedSet<HydrologicalYear> years;

    private final List<RiverStation> riverStations;

    public RiverLevelChartConfig(SortedSet<HydrologicalYear> years, List<RiverStation> riverStations) {
        this.years = years;
        this.riverStations = riverStations;
    }

    public SortedSet<HydrologicalYear> getYears() {
        return years;
    }

    public List<RiverStation> getRiverStations() {
        return riverStations;
    }
}
