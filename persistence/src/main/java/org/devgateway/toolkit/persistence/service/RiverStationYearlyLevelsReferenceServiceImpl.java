package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.repository.RiverStationYearlyLevelsReferenceRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class RiverStationYearlyLevelsReferenceServiceImpl extends BaseJpaServiceImpl<RiverStationYearlyLevelsReference>
        implements RiverStationYearlyLevelsReferenceService {

    @Autowired
    private RiverStationYearlyLevelsReferenceRepository riverStationYearlyLevelsReferenceRepository;

    @Override
    protected BaseJpaRepository<RiverStationYearlyLevelsReference, Long> repository() {
        return riverStationYearlyLevelsReferenceRepository;
    }

    @Override
    public RiverStationYearlyLevelsReference newInstance() {
        return new RiverStationYearlyLevelsReference();
    }

    @Override
    public boolean exists(RiverStation station, HydrologicalYear year, Long exceptId) {
        return riverStationYearlyLevelsReferenceRepository.existsByStationAndYearAndIdNot(station, year, exceptId);
    }
}
