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

    @CacheHibernateQueryResult
    @Query("select distinct b "
            + "from GTPBulletin b "
            + "join b.uploads "
            + "where b.year >= :startingYear")
    List<GTPBulletin> findAllWithUploads(int startingYear);
}
