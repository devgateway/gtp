package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class PovertyIndicatorServiceImpl extends  AbstractDatasetServiceImpl<PovertyIndicator>
        implements PovertyIndicatorService {

    @Autowired
    private PovertyIndicatorRepository repository;

    @Override
    protected AuditedEntityRepository<PovertyIndicator> repository() {
        return repository;
    }

    @Override
    public PovertyIndicator newInstance() {
        return new PovertyIndicator();
    }

}
