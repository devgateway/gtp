package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.GisIndicator;
import org.devgateway.toolkit.persistence.repository.ipar.category.GisIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class GisIndicatorServiceImpl extends BaseJpaServiceImpl<GisIndicator> implements GisIndicatorService {

    @Autowired
    private GisIndicatorRepository repository;

    @Override
    protected BaseJpaRepository<GisIndicator, Long> repository() {
        return repository;
    }

    @Override
    public GisIndicator newInstance() {
        return new GisIndicator();
    }

    @Override
    public JpaRepository<GisIndicator, Long> getRepository() {
        return repository;
    }

    @Override
    public Page<GisIndicator> searchText(String term, Pageable page) {
        if (term == null) {
            return repository.findAll(page);
        } else {
            return repository.findByLabel(term, page);
        }
    }
}
