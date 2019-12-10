package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.PovertyDataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.PovertyDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable) {
        return null;
    }
}
