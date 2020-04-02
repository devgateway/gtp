package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.IndicatorGroup;
import org.devgateway.toolkit.persistence.repository.ipar.category.IndicatorGroupRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
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
// @Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class IndicatorGroupServiceImpl extends BaseJpaServiceImpl<IndicatorGroup> implements IndicatorGroupService {

    @Autowired
    private IndicatorGroupRepository repository;

    @Override
    protected BaseJpaRepository<IndicatorGroup, Long> repository() {
        return repository;
    }

    @Override
    public IndicatorGroup newInstance() {
        return new IndicatorGroup();
    }

    @Override
    public Page<IndicatorGroup> findAll(final Specification<IndicatorGroup> spec, final Pageable pageable) {
        Page<IndicatorGroup> ret = repository().findAll(spec, pageable);
        ret.forEach(x -> x.getLocalizedLabels());
        return ret;
    }
}
