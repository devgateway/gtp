package org.devgateway.toolkit.persistence.dto.riverlevel;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelChartFilter {

    @NotNull
    @NotEmpty
    private final Set<HydrologicalYear> years;

    @NotNull
    private final Long riverStationId;

    public RiverLevelChartFilter(
            @JsonProperty("years") Set<HydrologicalYear> years,
            @JsonProperty("riverStationId") Long riverStationId) {
        this.years = years;
        this.riverStationId = riverStationId;
    }

    public Set<HydrologicalYear> getYears() {
        return years;
    }

    public Long getRiverStationId() {
        return riverStationId;
    }
}
