package org.devgateway.toolkit.persistence.dto.riverlevel;

import java.util.List;
import java.util.SortedSet;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dto.ChartConfig;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelChartConfig extends ChartConfig {

    private final SortedSet<HydrologicalYear> years;

    private final List<RiverStation> riverStations;

    public RiverLevelChartConfig(
            String organization,
            SortedSet<HydrologicalYear> years,
            List<RiverStation> riverStations) {
        super(organization);
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
