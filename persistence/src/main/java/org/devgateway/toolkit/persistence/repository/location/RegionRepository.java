package org.devgateway.toolkit.persistence.repository.location;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegionRepository extends TextSearchableRepository<Region, Long>, BaseJpaRepository<Region, Long> {

    @Query("select r from Region r where lower(r.name) like %:name%")
    Region findByName(@Param("name") String name);

    @Query("select r from Region r where lower(r.code) like %:code%")
    Region findByCode(@Param("code") String code);

    @Override
    @Query("select o from Region o where lower(o.name) like %?1%")
    Page<Region> searchText(String code, Pageable page);

    @Override
    @CacheHibernateQueryResult
    List<Region> findAll();
}
