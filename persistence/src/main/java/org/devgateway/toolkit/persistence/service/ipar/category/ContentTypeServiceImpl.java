package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.ContentType;
import org.devgateway.toolkit.persistence.repository.ipar.category.ContentTypeRepository;
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
public class ContentTypeServiceImpl extends BaseJpaServiceImpl<ContentType> implements ContentTypeService {

    @Autowired
    private ContentTypeRepository repository;

    @Override
    protected BaseJpaRepository<ContentType, Long> repository() {
        return repository;
    }

    @Override
    public ContentType newInstance() {
        return new ContentType();
    }
}
