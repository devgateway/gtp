package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.PersistedCollectionSize;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
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

    @CacheHibernateQueryResult
    @Query("select new org.devgateway.toolkit.persistence.dao.PersistedCollectionSize(p.id, count(pq.id)) "
            + "from ProductYearlyPrices p "
            + "join p.quantities pq "
            + "where p.id in :ids "
            + "group by p.id")
    List<PersistedCollectionSize> getQuantitySizes(Collection<Long> ids);

    @CacheHibernateQueryResult
    @Query("select p "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where yp.year = :year "
            + "and p.product.id = :productId "
            + "and p.market.id = :marketId "
            + "and p.priceType in :priceTypes")
    List<ProductPrice> findPrices(Integer year, Long productId, List<PriceType> priceTypes, Long marketId);

    @CacheHibernateQueryResult
    @Query("select new org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice(p.priceType, avg(p.price)) "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where yp.year = :year "
            + "and p.product.id = :productId "
            + "and p.market.id = :marketId "
            + "and p.priceType in :priceTypes "
            + "group by p.priceType")
    List<AveragePrice> findAveragePrices(Integer year, Long productId, List<PriceType> priceTypes, Long marketId);

    @CacheHibernateQueryResult
    @Query("select yp.year "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "group by yp.year "
            + "having count(p.id) > 0")
    List<Integer> findYearsWithPrices();

    @CacheHibernateQueryResult
    @Query("select count(p.id) "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where yp.year = :year "
            + "and p.product.id = :productId")
    Long countPrices(Integer year, Long productId);

    @CacheHibernateQueryResult
    @Query("select distinct p.product "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where yp.year = :year "
            + "order by p.product.name")
    List<Product> getProductsWithPrices(Integer year);

    @CacheHibernateQueryResult
    @Query("select distinct p.market "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where yp.year = :year "
            + "and p.product.id = :productId "
            + "order by p.market.name")
    List<Market> getMarketsWithPrices(Integer year, Long productId);

    @CacheHibernateQueryResult
    boolean existsByYear(Integer year);

    @CacheHibernateQueryResult
    @Query("select count(p.id) "
            + "from ProductYearlyPrices yp "
            + "join yp.prices p "
            + "where p.product.id = :productId "
            + "and p.priceType.id in :priceTypeIds")
    Long countPricesForProductAndPriceType(Long productId, Collection<Long> priceTypeIds);

    @CacheHibernateQueryResult
    @Query("select yp.year "
            + "from ProductYearlyPrices yp "
            + "join yp.quantities q "
            + "group by yp.year "
            + "having count(q.id) > 0")
    List<Integer> findYearsWithQuantities();

    @CacheHibernateQueryResult
    @Query("select distinct q.market "
            + "from ProductYearlyPrices yp "
            + "join yp.quantities q "
            + "where yp.year = :year "
            + "and q.product.productType.id = :productTypeId "
            + "order by q.market.name")
    List<Market> getMarketsWithQuantities(Integer year, Long productTypeId);

    @CacheHibernateQueryResult
    @Query("select distinct q.product.productType "
            + "from ProductYearlyPrices yp "
            + "join yp.quantities q "
            + "where yp.year = :year "
            + "order by q.product.productType.label")
    List<ProductType> getProductTypesWithQuantities(Integer year);

    @CacheHibernateQueryResult
    @Query("select q "
            + "from ProductYearlyPrices yp "
            + "join yp.quantities q "
            + "where yp.year = :year "
            + "and q.product.productType.id = :productTypeId "
            + "and q.market.id = :marketId")
    List<ProductQuantity> findQuantities(Integer year, Long productTypeId, Long marketId);
}
