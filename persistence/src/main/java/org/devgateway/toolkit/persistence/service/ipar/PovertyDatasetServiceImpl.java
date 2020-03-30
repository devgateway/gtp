package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.PovertyDataset;
import org.devgateway.toolkit.persistence.repository.ipar.PovertyDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
// @Service("povertyDatasetService")
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

}
