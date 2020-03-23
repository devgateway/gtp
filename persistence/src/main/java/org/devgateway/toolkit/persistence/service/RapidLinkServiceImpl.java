package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.RapidLink;
import org.devgateway.toolkit.persistence.repository.ipar.RapidLinkRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<RapidLink> findByRapidLinkPositionId(Long id) {
        return repository.findByRapidLinkPositionId(id);
    }

    @Override
    public Iterable<RapidLink> findByRapidLinkPositionIdNotNull() {
        return repository.findByRapidLinkPositionIdNotNull();
    }

    @Override
    public List<RapidLink> findAll() {
        return repository.findAllPopulatedLang();
    }
}
