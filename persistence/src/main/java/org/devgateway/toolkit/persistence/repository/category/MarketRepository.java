package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.ipar.Market;
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
    boolean existsByDepartmentAndNameAndIdNot(Department department, String name, Long exceptId);

    @CacheHibernateQueryResult
    boolean existsByLatitudeAndLongitudeAndIdNot(Double latitude, Double longitude, Long exceptId);
}
