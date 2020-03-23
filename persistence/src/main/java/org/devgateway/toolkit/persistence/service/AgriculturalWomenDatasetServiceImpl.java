package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenDataset;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenDatasetRepository;
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
@Service("agriculturalWomenDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AgriculturalWomenDatasetServiceImpl extends BaseJpaServiceImpl<AgriculturalWomenDataset>
        implements DatasetService<AgriculturalWomenDataset>  {

    @Autowired
    private AgriculturalWomenDatasetRepository repository;

    @Override
    protected BaseJpaRepository<AgriculturalWomenDataset, Long> repository() {
        return repository;
    }

    @Override
    public AgriculturalWomenDataset newInstance() {
        return new AgriculturalWomenDataset();
    }

    @Override
    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang) {
        return null;
    }

}
