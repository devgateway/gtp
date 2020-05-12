package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsReferenceRepository
        extends BaseJpaRepository<RiverStationYearlyLevelsReference, Long> {

    boolean existsByStationAndYearAndIdNot(RiverStation station, HydrologicalYear year, Long exceptId);
}
