package org.devgateway.toolkit.persistence.repository.reference;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsReferenceRepository
        extends BaseJpaRepository<RiverStationYearlyLevelsReference, Long> {

    @CacheHibernateQueryResult
    boolean existsByStationAndYearAndIdNot(RiverStation station, HydrologicalYear year, Long exceptId);

    @CacheHibernateQueryResult
    List<RiverStationYearlyLevelsReference> findByStationId(Long riverStationId);
}
