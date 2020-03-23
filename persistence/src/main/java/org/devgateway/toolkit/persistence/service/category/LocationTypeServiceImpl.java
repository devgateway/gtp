package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.LocationType;
import org.devgateway.toolkit.persistence.repository.ipar.category.LocationTypeRepository;
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
public class LocationTypeServiceImpl extends BaseJpaServiceImpl<LocationType> implements LocationTypeService {

    @Autowired
    private LocationTypeRepository repository;

    @Override
    protected BaseJpaRepository<LocationType, Long> repository() {
        return repository;
    }

    @Override
    public LocationType newInstance() {
        return new LocationType();
    }
}
