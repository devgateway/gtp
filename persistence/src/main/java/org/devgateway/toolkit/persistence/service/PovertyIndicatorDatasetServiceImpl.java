package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.PovertyIndicatorDataset;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class PovertyIndicatorDatasetServiceImpl extends BaseJpaServiceImpl<PovertyIndicatorDataset>
        implements PovertyIndicatorDatasetService  {

    @Autowired
    private PovertyIndicatorDatasetRepository repository;

    @Override
    protected BaseJpaRepository<PovertyIndicatorDataset, Long> repository() {
        return repository;
    }

    @Override
    public PovertyIndicatorDataset newInstance() {
        return new PovertyIndicatorDataset();
    }
}
