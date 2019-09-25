package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.repository.category.AgriculturalWomenGroupRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AgriculturalWomenGroupServiceImpl extends BaseJpaServiceImpl<AgriculturalWomenGroup>
        implements AgriculturalWomenGroupService {

    @Autowired
    private AgriculturalWomenGroupRepository repository;

    @Override
    protected BaseJpaRepository<AgriculturalWomenGroup, Long> repository() {
        return repository;
    }

    @Override
    public AgriculturalWomenGroup newInstance() {
        return new AgriculturalWomenGroup();
    }
}
