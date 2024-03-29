package org.devgateway.toolkit.persistence.repository.category;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface ProductRepository extends BaseJpaRepository<Product, Long>,
        TextSearchableRepository<Product, Long> {

    @CacheHibernateQueryResult
    List<Product> findByProductType(ProductType productType);

    @Override
    @Query("select p from Product p where lower(p.name) like %:term%")
    Page<Product> searchText(String term, Pageable page);

    @CacheHibernateQueryResult
    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(p.id, p.name) "
            + "from Product p "
            + "where p.productType = :productType")
    List<Pair<Long, String>> findAllNames(ProductType productType);

    @CacheHibernateQueryResult
    boolean existsByProductType(ProductType productType);
}
