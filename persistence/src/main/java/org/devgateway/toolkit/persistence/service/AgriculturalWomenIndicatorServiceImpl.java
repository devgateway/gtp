package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AgriculturalWomenIndicatorServiceImpl extends BaseJpaServiceImpl<AgriculturalWomenIndicator>
        implements AgriculturalWomenIndicatorService {

    @Autowired
    private AgriculturalWomenIndicatorRepository repository;

    @Override
    protected BaseJpaRepository<AgriculturalWomenIndicator, Long> repository() {
        return repository;
    }

    @Override
    public AgriculturalWomenIndicator newInstance() {
        return new AgriculturalWomenIndicator();
    }
}
