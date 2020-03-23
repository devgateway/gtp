package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.CropSubType;
import org.devgateway.toolkit.persistence.repository.ipar.category.CropSubTypeRepository;
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
public class CropSubTypeServiceImpl extends BaseJpaServiceImpl<CropSubType> implements CropSubTypeService {

    @Autowired
    private CropSubTypeRepository cropSubTypeRepository;

    @Override
    protected BaseJpaRepository<CropSubType, Long> repository() {
        return cropSubTypeRepository;
    }

    @Override
    public CropSubType newInstance() {
        return new CropSubType();
    }
}
