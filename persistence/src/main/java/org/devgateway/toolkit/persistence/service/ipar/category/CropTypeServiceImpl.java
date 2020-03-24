package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.repository.ipar.category.CropTypeRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author idobre
 * @since 2019-03-04
 */
// @Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class CropTypeServiceImpl extends BaseJpaServiceImpl<CropType> implements CropTypeService {

    @Autowired
    private CropTypeRepository cropTypeRepository;

    @Override
    protected BaseJpaRepository<CropType, Long> repository() {
        return cropTypeRepository;
    }

    @Override
    public CropType newInstance() {
        return new CropType();
    }
}
