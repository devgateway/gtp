package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.MethodOfEnforcement;
import org.devgateway.toolkit.persistence.repository.ipar.category.MethodOfEnforcementRepository;
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
public class MethodOfEnforcementServiceImpl extends BaseJpaServiceImpl<MethodOfEnforcement>
        implements MethodOfEnforcementService {

    @Autowired
    private MethodOfEnforcementRepository repository;

    @Override
    protected BaseJpaRepository<MethodOfEnforcement, Long> repository() {
        return repository;
    }

    @Override
    public MethodOfEnforcement newInstance() {
        return new MethodOfEnforcement();
    }
}
