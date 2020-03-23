package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexDataset;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.AOIDatasetRepository;
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
@Service("aoiDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AOIDatasetServiceImpl extends BaseJpaServiceImpl<AgricultureOrientationIndexDataset>
        implements DatasetService<AgricultureOrientationIndexDataset>  {

    @Autowired
    private AOIDatasetRepository repository;

    @Override
    protected BaseJpaRepository<AgricultureOrientationIndexDataset, Long> repository() {
        return repository;
    }

    @Override
    public AgricultureOrientationIndexDataset newInstance() {
        return new AgricultureOrientationIndexDataset();
    }

    @Override
    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang) {
        return null;
    }
}
