package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.repository.category.ProfessionalActivityRepository;
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
public class ProfessionalActivityServiceImpl extends BaseJpaServiceImpl<ProfessionalActivity>
        implements ProfessionalActivityService {

    @Autowired
    private ProfessionalActivityRepository repository;

    @Override
    protected BaseJpaRepository<ProfessionalActivity, Long> repository() {
        return repository;
    }

    @Override
    public ProfessionalActivity newInstance() {
        return new ProfessionalActivity();
    }
}
