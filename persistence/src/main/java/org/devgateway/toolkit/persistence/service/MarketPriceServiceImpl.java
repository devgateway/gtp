package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.repository.MarketPriceRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class MarketPriceServiceImpl extends AbstractDatasetServiceImpl<MarketPrice> implements MarketPriceService {

    @Autowired
    private MarketPriceRepository repository;

    @Override
    protected AuditedEntityRepository<MarketPrice> repository() {
        return repository;
    }

    @Override
    public MarketPrice newInstance() {
        return new MarketPrice();
    }
}
