package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.PartnerGroup;
import org.devgateway.toolkit.persistence.repository.ipar.category.PartnerGroupRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
// @Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class PartnerGroupServiceImpl extends BaseJpaServiceImpl<PartnerGroup>
        implements PartnerGroupService {

    @Autowired
    private PartnerGroupRepository repository;

    @Override
    protected BaseJpaRepository<PartnerGroup, Long> repository() {
        return repository;
    }

    @Override
    public PartnerGroup newInstance() {
        return new PartnerGroup();
    }
}
