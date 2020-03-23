package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.devgateway.toolkit.persistence.repository.ProductionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
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
public class ProductionServiceImpl extends AbstractDatasetServiceImpl<Production> implements ProductionService {

    @Autowired
    private ProductionRepository repository;

    @Override
    protected AuditedEntityRepository<Production> repository() {
        return repository;
    }

    @Override
    public Production newInstance() {
        return new Production();
    }
}
