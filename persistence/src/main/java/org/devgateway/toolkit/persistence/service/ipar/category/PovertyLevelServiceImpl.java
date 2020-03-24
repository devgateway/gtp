package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.PovertyLevel;
import org.devgateway.toolkit.persistence.repository.ipar.category.PovertyLevelRepository;
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
public class PovertyLevelServiceImpl extends BaseJpaServiceImpl<PovertyLevel>
        implements PovertyLevelService {

    @Autowired
    private PovertyLevelRepository repository;

    @Override
    protected BaseJpaRepository<PovertyLevel, Long> repository() {
        return repository;
    }

    @Override
    public PovertyLevel newInstance() {
        return new PovertyLevel();
    }
}
