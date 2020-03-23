package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.repository.ipar.AgriculturalWomenIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AgriculturalWomenIndicatorServiceImpl extends AbstractDatasetServiceImpl<AgriculturalWomenIndicator>
        implements AgriculturalWomenIndicatorService {

    @Autowired
    private AgriculturalWomenIndicatorRepository repository;

    @Override
    protected AuditedEntityRepository<AgriculturalWomenIndicator> repository() {
        return repository;
    }

    @Override
    public AgriculturalWomenIndicator newInstance() {
        return new AgriculturalWomenIndicator();
    }
}
