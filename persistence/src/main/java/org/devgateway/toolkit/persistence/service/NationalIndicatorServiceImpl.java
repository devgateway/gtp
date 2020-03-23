package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.NationalIndicator;
import org.devgateway.toolkit.persistence.repository.ipar.NationalIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class NationalIndicatorServiceImpl extends BaseJpaServiceImpl<NationalIndicator>
        implements NationalIndicatorService {

    @Autowired
    private NationalIndicatorRepository repository;

    @Override
    protected BaseJpaRepository<NationalIndicator, Long> repository() {
        return repository;
    }

    @Override
    public NationalIndicator newInstance() {
        return new NationalIndicator();
    }
}
