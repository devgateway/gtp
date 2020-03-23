package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.LossType;
import org.devgateway.toolkit.persistence.repository.ipar.category.LossTypeRepository;
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
public class LossTypeServiceImpl extends BaseJpaServiceImpl<LossType> implements LossTypeService {

    @Autowired
    private LossTypeRepository repository;

    @Override
    protected BaseJpaRepository<LossType, Long> repository() {
        return repository;
    }

    @Override
    public LossType newInstance() {
        return new LossType();
    }
}
