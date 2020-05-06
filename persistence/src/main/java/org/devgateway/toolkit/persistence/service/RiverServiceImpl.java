package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.River;
import org.devgateway.toolkit.persistence.repository.RiverRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class RiverServiceImpl extends BaseJpaServiceImpl<River> implements RiverService {

    @Autowired
    private RiverRepository riverRepository;

    @Override
    protected BaseJpaRepository<River, Long> repository() {
        return riverRepository;
    }

    @Override
    public River newInstance() {
        return new River();
    }

    @Override
    public UniquePropertyRepository<River, Long> uniquePropertyRepository() {
        return riverRepository;
    }

    @Override
    public JpaRepository<River, Long> getRepository() {
        return riverRepository;
    }

    @Override
    public Page<River> searchText(String term, Pageable page) {
        return riverRepository.searchText(term, page);
    }
}
