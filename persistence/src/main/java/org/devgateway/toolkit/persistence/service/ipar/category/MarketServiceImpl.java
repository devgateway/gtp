package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.Market;
import org.devgateway.toolkit.persistence.repository.ipar.MarketRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
// @Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class MarketServiceImpl extends BaseJpaServiceImpl<Market> implements MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    protected BaseJpaRepository<Market, Long> repository() {
        return marketRepository;
    }

    @Override
    public Market newInstance() {
        return new Market();
    }

    @Override
    public Market findByName(String departmentName, String marketName) {
        return marketRepository.findByName(departmentName, marketName);
    }
}
