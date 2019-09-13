package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "servicesCache")
@Transactional
public interface RegionRepository extends BaseJpaRepository<Region, Long> {

    @Cacheable
    @Query("select r from Region r where lower(r.name) like %:name%")
    Region searchByName(@Param("name") String name);

    @Cacheable
    @Query("select r from Region r where lower(r.code) like %:code%")
    Region searchByCode(@Param("code") String code);
}
