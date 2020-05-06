package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.River;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverRepository extends UniquePropertyRepository<River, Long>, TextSearchableRepository<River, Long> {

    @Override
    @Query("select r from River r where lower(r.name) like %:term%")
    @CacheHibernateQueryResult
    Page<River> searchText(String term, Pageable page);
}
