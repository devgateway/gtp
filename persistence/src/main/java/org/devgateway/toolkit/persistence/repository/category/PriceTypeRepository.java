package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface PriceTypeRepository extends CategoryRepository<PriceType>,
        TextSearchableRepository<PriceType, Long> {

    @CacheHibernateQueryResult
    List<PriceType> findAllByIdIn(List<Long> ids);

    @CacheHibernateQueryResult
    PriceType findByName(String name);
}
