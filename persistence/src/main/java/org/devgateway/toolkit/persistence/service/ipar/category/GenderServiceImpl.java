package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender;
import org.devgateway.toolkit.persistence.repository.ipar.category.GenderRepository;
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
public class GenderServiceImpl extends BaseJpaServiceImpl<Gender> implements GenderService {

    @Autowired
    private GenderRepository repository;

    @Override
    protected BaseJpaRepository<Gender, Long> repository() {
        return repository;
    }

    @Override
    public Gender newInstance() {
        return new Gender();
    }
}
