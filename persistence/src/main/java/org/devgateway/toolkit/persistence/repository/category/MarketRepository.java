package org.devgateway.toolkit.persistence.repository.category;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface MarketRepository extends BaseJpaRepository<Market, Long> {

    @CacheHibernateQueryResult
    boolean existsByDepartmentAndTypeAndNameAndIdNot(Department department, MarketType marketType, String name,
            Long exceptId);

    @CacheHibernateQueryResult
    boolean existsByLatitudeAndLongitudeAndIdNot(Double latitude, Double longitude, Long exceptId);

    @CacheHibernateQueryResult
    List<Market> findByType_Name(String marketTypeName);
}
