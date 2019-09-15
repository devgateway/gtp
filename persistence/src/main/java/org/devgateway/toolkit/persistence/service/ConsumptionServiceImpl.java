package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.repository.ConsumptionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ConsumptionServiceImpl extends BaseJpaServiceImpl<Consumption> implements ConsumptionService {

    @Autowired
    private ConsumptionRepository repository;

    @Override
    protected BaseJpaRepository<Consumption, Long> repository() {
        return repository;
    }

    @Override
    public Consumption newInstance() {
        return new Consumption();
    }
}
