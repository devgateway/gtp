package org.devgateway.toolkit.persistence.repository;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPBulletinRepository extends BaseJpaRepository<AnnualGTPBulletin, Long> {

    @CacheHibernateQueryResult
    @Query("select distinct year from AnnualGTPBulletin")
    List<Integer> findAllYears();
}
