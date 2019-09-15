package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.repository.ProductionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ProductionServiceImpl extends BaseJpaServiceImpl<Production> implements ProductionService {

    @Autowired
    private ProductionRepository repository;

    @Override
    protected BaseJpaRepository<Production, Long> repository() {
        return repository;
    }

    @Override
    public Production newInstance() {
        return new Production();
    }
}
