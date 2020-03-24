package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.ProductionDataset;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.ipar.ProductionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
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
// @Service("productionDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ProductionDatasetServiceImpl extends BaseJpaServiceImpl<ProductionDataset>
        implements DatasetService<ProductionDataset>  {

    @Autowired
    private ProductionDatasetRepository productionDatasetRepository;

    @Override
    protected BaseJpaRepository<ProductionDataset, Long> repository() {
        return productionDatasetRepository;
    }

    @Override
    public ProductionDataset newInstance() {
        return new ProductionDataset();
    }

    @Override
    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang) {
        return null;
    }
}
