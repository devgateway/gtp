package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.List;
import java.util.Set;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsRepository extends BaseJpaRepository<RiverStationYearlyLevels, Long> {

    @CacheHibernateQueryResult
    List<RiverStationYearlyLevels> findByYear(HydrologicalYear year);

    @CacheHibernateQueryResult
    @Query("select distinct yl.year "
            + "from RiverStationYearlyLevels yl "
            + "join yl.levels l")
    List<HydrologicalYear> findYearsWithLevels();

    @CacheHibernateQueryResult
    @Query("select distinct yl.station "
            + "from RiverStationYearlyLevels yl "
            + "join yl.levels l")
    List<RiverStation> findStationsWithLevels();

    @CacheHibernateQueryResult
    List<RiverStationYearlyLevels> findByYearInAndStationId(Set<HydrologicalYear> years, Long riverStationId);
}
