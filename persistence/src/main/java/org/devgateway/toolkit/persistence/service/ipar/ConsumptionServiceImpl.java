package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Consumption;
import org.devgateway.toolkit.persistence.repository.ipar.ConsumptionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ConsumptionServiceImpl extends AbstractDatasetServiceImpl<Consumption> implements ConsumptionService {

    @Autowired
    private ConsumptionRepository repository;

    @Override
    protected AuditedEntityRepository<Consumption> repository() {
        return repository;
    }

    @Override
    public Consumption newInstance() {
        return new Consumption();
    }
}
