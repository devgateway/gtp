package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.FoodLossDataset;
import org.devgateway.toolkit.persistence.repository.FoodLossDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service("foodLossDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class FoodLossDatasetServiceImpl extends BaseJpaServiceImpl<FoodLossDataset>
        implements DatasetService<FoodLossDataset>  {

    @Autowired
    private FoodLossDatasetRepository repository;

    @Override
    protected BaseJpaRepository<FoodLossDataset, Long> repository() {
        return repository;
    }

    @Override
    public FoodLossDataset newInstance() {
        return new FoodLossDataset();
    }
}
