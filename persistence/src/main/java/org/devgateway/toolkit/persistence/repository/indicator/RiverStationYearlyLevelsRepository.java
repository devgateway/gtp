package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsRepository extends BaseJpaRepository<RiverStationYearlyLevels, Long> {

    @CacheHibernateQueryResult
    List<RiverStationYearlyLevels> findByYear(HydrologicalYear year);
}
