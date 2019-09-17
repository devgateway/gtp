package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ConsumptionDataset;
import org.devgateway.toolkit.persistence.repository.ConsumptionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service("consumptionDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ConsumptionDatasetServiceImpl extends BaseJpaServiceImpl<ConsumptionDataset>
        implements DatasetService<ConsumptionDataset>  {

    @Autowired
    private ConsumptionDatasetRepository consumptionDatasetRepository;

    @Override
    protected BaseJpaRepository<ConsumptionDataset, Long> repository() {
        return consumptionDatasetRepository;
    }

    @Override
    public ConsumptionDataset newInstance() {
        return new ConsumptionDataset();
    }
}
