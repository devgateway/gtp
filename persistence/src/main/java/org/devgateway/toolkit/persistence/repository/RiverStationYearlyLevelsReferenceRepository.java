package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsReferenceRepository
        extends BaseJpaRepository<RiverStationYearlyLevelsReference, Long> {

    @Query("select count(id) "
            + "from RiverStationYearlyLevelsReference "
            + "where station=:station "
            + "and year=:year "
            + "and id<>:exceptId")
    long count(RiverStation station, HydrologicalYear year, Long exceptId);
}
