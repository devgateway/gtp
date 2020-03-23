package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.repository.ipar.GisSettingsRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class GisSettingsServiceImpl extends BaseJpaServiceImpl<GisSettings> implements GisSettingsService {
    @Autowired
    private GisSettingsRepository repository;

    @Override
    protected BaseJpaRepository<GisSettings, Long> repository() {
        return repository;
    }

    @Override
    public GisSettings newInstance() {
        return new GisSettings();
    }

}
