package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
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
            + "join yl.levels l "
            + "where yl.year >= :minYear")
    List<HydrologicalYear> findYearsWithLevels(HydrologicalYear minYear);

    @CacheHibernateQueryResult
    @Query("select distinct yl.station "
            + "from RiverStationYearlyLevels yl "
            + "join yl.levels l "
            + "order by yl.station.name")
    List<RiverStation> findStationsWithLevels();

    @CacheHibernateQueryResult
    @Query("select distinct yl.station "
            + "from RiverStationYearlyLevels yl "
            + "join yl.levels l "
            + "where yl.year in :years "
            + "order by yl.station.name")
    List<RiverStation> findStationsWithLevels(Collection<HydrologicalYear> years);

    @CacheHibernateQueryResult
    @Query("select count(l.id) "
            + "from RiverStationYearlyLevels yl "
            + "join yl.levels l "
            + "where yl.year in :years "
            + "and yl.station.id = :riverStationId")
    Long countLevels(Collection<HydrologicalYear> years, Long riverStationId);

    @CacheHibernateQueryResult
    List<RiverStationYearlyLevels> findByYearInAndStationId(Set<HydrologicalYear> years, Long riverStationId);
}
