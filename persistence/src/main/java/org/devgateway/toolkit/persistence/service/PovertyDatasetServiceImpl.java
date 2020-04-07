package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.PovertyDataset;
import org.devgateway.toolkit.persistence.repository.PovertyDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service("povertyDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class PovertyDatasetServiceImpl extends BaseJpaServiceImpl<PovertyDataset>
        implements DatasetService<PovertyDataset>  {

    @Autowired
    private PovertyDatasetRepository repository;

    @Override
    protected BaseJpaRepository<PovertyDataset, Long> repository() {
        return repository;
    }

    @Override
    public PovertyDataset newInstance() {
        return new PovertyDataset();
    }

    @Override
    @CacheEvict(value = "povertyCache", allEntries = true)
    public PovertyDataset saveAndFlush(PovertyDataset dataset) {
        return super.saveAndFlush(dataset);
    }

    @Override
    @CacheEvict(value = "povertyCache", allEntries = true)
    public void delete(PovertyDataset dataset) {
        super.delete(dataset);
    }

}
