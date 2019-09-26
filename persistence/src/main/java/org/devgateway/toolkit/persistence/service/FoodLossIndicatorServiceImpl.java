package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.repository.FoodLossIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class FoodLossIndicatorServiceImpl extends  AbstractDatasetServiceImpl<FoodLossIndicator>
        implements FoodLossIndicatorService {

    @Autowired
    private FoodLossIndicatorRepository repository;

    @Override
    protected AuditedEntityRepository<FoodLossIndicator> repository() {
        return repository;
    }

    @Override
    public FoodLossIndicator newInstance() {
        return new FoodLossIndicator();
    }
}
