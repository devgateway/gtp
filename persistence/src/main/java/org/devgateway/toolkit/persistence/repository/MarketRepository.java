package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface MarketRepository extends BaseJpaRepository<Market, Long> {

    @Query("select m from Market m join m.department d where lower(m.name) = :mkName and lower(d.name) = :dpName")
    Market findByName(@Param("dpName") String departmentName, @Param("mkName") String marketName);
}
