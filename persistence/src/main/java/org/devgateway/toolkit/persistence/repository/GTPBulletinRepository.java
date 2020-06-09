package org.devgateway.toolkit.persistence.repository;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPBulletinRepository extends BaseJpaRepository<GTPBulletin, Long> {

    @CacheHibernateQueryResult
    @Query("select distinct year from GTPBulletin")
    List<Integer> findAllYears();
}
