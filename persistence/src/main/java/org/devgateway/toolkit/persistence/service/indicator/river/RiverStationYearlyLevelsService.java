package org.devgateway.toolkit.persistence.service.indicator.river;

import java.util.List;
import java.util.Set;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsService
        extends YearIndicatorGenerator<RiverStationYearlyLevels, HydrologicalYear> {

    List<HydrologicalYear> findYearsWithLevels();

    List<RiverStation> findStationsWithLevels();

    List<RiverStationYearlyLevels> findByYearInAndStationId(Set<HydrologicalYear> years, Long riverStationId);

    boolean hasLevels(Set<HydrologicalYear> years, Long riverStationId);

    List<RiverStation> findStationsWithLevels(Set<HydrologicalYear> years);
}
