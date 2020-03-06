package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.repository.category.IndexTypeRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class IndexTypeServiceImpl extends BaseJpaServiceImpl<IndexType> implements IndexTypeService {
    @Autowired
    private IndexTypeRepository repository;

    @Override
    protected BaseJpaRepository<IndexType, Long> repository() {
        return repository;
    }

    @Override
    public IndexType newInstance() {
        return new IndexType();
    }

    @Override
    public Optional<IndexType> findByCategoryTypeId(int typeId) {
        return repository.findByType(typeId);
    }
}
