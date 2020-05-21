package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationRepository extends UniquePropertyRepository<RiverStation, Long>,
        TextSearchableRepository<RiverStation, Long> {

    @CacheHibernateQueryResult
    @Query("select rs from RiverStation rs where lower(rs.name) like %:term%")
    Page<RiverStation> searchText(String term, Pageable page);
}
