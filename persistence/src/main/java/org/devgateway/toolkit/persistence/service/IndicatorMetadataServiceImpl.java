package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.IndicatorMetadataRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public IndicatorMetadata findByIndicatorType(Integer type) {
        List<IndicatorMetadata> list = repository.findByIndicatorType(type);
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public IndicatorMetadata newInstance() {
        return new IndicatorMetadata();
    }
}
