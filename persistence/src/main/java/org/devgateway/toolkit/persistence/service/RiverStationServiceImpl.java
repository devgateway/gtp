package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.repository.RiverStationRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class RiverStationServiceImpl extends BaseJpaServiceImpl<RiverStation> implements RiverStationService {

    @Autowired
    private RiverStationRepository riverStationRepository;

    @Override
    protected BaseJpaRepository<RiverStation, Long> repository() {
        return riverStationRepository;
    }

    @Override
    public RiverStation newInstance() {
        return new RiverStation();
    }

    @Override
    public UniquePropertyRepository<RiverStation, Long> uniquePropertyRepository() {
        return riverStationRepository;
    }
}
