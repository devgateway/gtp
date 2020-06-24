package org.devgateway.toolkit.persistence.repository.category;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface MarketRepository extends BaseJpaRepository<Market, Long> {

    @CacheHibernateQueryResult
    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(m.id, m.name) "
            + "from Market m "
            + "where m.department = :department "
            + "and m.type = :marketType")
    List<Pair<Long, String>> findAllNames(Department department, MarketType marketType);

    @CacheHibernateQueryResult
    boolean existsByLatitudeAndLongitudeAndIdNot(Double latitude, Double longitude, Long exceptId);

    @CacheHibernateQueryResult
    List<Market> findByType_Name(String marketTypeName);
}
