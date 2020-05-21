package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.repository.RiverStationRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Override
    public JpaRepository<RiverStation, Long> getRepository() {
        return riverStationRepository;
    }

    @Override
    public Page<RiverStation> searchText(String term, Pageable page) {
        return riverStationRepository.searchText(term, page);
    }
}
