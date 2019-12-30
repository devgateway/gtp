package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.repository.RegionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class RegionIndicatorServiceImpl extends BaseJpaServiceImpl<RegionIndicator> implements RegionIndicatorService {
    @Autowired
    private RegionIndicatorRepository repository;

    @Override
    protected BaseJpaRepository<RegionIndicator, Long> repository() {
        return repository;
    }

    @Override
    public RegionIndicator newInstance() {
        return new RegionIndicator();
    }
}
