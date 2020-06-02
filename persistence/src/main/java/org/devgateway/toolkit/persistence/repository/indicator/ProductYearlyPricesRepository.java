package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.PersistedCollectionSize;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface ProductYearlyPricesRepository extends BaseJpaRepository<ProductYearlyPrices, Long> {

    @CacheHibernateQueryResult
    @Query("select new org.devgateway.toolkit.persistence.dao.PersistedCollectionSize(p.id, count(pp.id)) "
            + "from ProductYearlyPrices p "
            + "join p.prices pp "
            + "where p.id in :ids "
            + "group by p.id")
    List<PersistedCollectionSize> getPriceSizes(Collection<Long> ids);
}
