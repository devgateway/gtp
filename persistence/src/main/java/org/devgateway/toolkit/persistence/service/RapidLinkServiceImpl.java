package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.repository.RapidLinkRepository;
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
public class RapidLinkServiceImpl extends BaseJpaServiceImpl<RapidLink> implements RapidLinkService {

    @Autowired
    private RapidLinkRepository repository;

    @Override
    protected BaseJpaRepository<RapidLink, Long> repository() {
        return repository;
    }

    @Override
    public RapidLink newInstance() {
        return new RapidLink();
    }
}