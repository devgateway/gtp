package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.AgeGroup;
import org.devgateway.toolkit.persistence.repository.category.AgeGroupRepository;
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
public class AgeGroupServiceImpl extends BaseJpaServiceImpl<AgeGroup> implements AgeGroupService {

    @Autowired
    private AgeGroupRepository repository;

    @Override
    protected BaseJpaRepository<AgeGroup, Long> repository() {
        return repository;
    }

    @Override
    public AgeGroup newInstance() {
        return new AgeGroup();
    }
}
