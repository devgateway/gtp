package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.ProductionDataset;
import org.devgateway.toolkit.persistence.repository.ipar.ProductionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(value = "productionCache", allEntries = true)
    public ProductionDataset saveAndFlush(ProductionDataset dataset) {
        return super.saveAndFlush(dataset);
    }

    @Override
    @CacheEvict(value = "productionCache", allEntries = true)
    public void delete(ProductionDataset dataset) {
        super.delete(dataset);
    }

}
