package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.WebContent;
import org.devgateway.toolkit.persistence.repository.ipar.WebContentRepository;
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
public class WebContentServiceImpl extends BaseJpaServiceImpl<WebContent> implements WebContentService {

    @Autowired
    private WebContentRepository repository;

    @Override
    protected BaseJpaRepository<WebContent, Long> repository() {
        return repository;
    }

    @Override
    public WebContent newInstance() {
        return new WebContent();
    }
}
