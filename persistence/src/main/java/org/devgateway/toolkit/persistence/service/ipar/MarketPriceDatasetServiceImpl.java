package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.MarketDataset;
import org.devgateway.toolkit.persistence.repository.ipar.MarketPriceDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
// @Service("marketPriceDatasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class MarketPriceDatasetServiceImpl extends BaseJpaServiceImpl<MarketDataset>
        implements DatasetService<MarketDataset>  {

    @Autowired
    private MarketPriceDatasetRepository repository;

    @Override
    protected BaseJpaRepository<MarketDataset, Long> repository() {
        return repository;
    }

    @Override
    public MarketDataset newInstance() {
        return new MarketDataset();
    }

}
