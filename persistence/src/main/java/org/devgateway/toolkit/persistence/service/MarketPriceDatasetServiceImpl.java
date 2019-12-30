package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.MarketDataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.MarketPriceDatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service("marketPriceDatasetService")
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

    @Override
    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang) {
        return null;
    }
}
