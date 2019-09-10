package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.repository.MarketRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class MarketServiceImpl extends BaseJpaServiceImpl<Market> implements MarketService  {

    @Autowired
    MarketRepository repository;

    @Override
    protected BaseJpaRepository<Market, Long> repository() {
        return repository;
    }

    @Override
    public Market newInstance() {
        return new Market();
    }
}
