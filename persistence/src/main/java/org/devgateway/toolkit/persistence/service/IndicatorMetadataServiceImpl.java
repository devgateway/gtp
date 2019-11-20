package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.IndicatorMetadataRepository;
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
public class IndicatorMetadataServiceImpl extends BaseJpaServiceImpl<IndicatorMetadata>
        implements IndicatorMetadataService {

    @Autowired
    private IndicatorMetadataRepository repository;

    @Override
    protected BaseJpaRepository<IndicatorMetadata, Long> repository() {
        return repository;
    }

    @Override
    public IndicatorMetadata newInstance() {
        return new IndicatorMetadata();
    }
}
