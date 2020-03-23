package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ipar.Department;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "servicesCache")
@Transactional
public interface DepartmentRepository extends BaseJpaRepository<Department, Long> {

    @Cacheable
    @Query("select r from Department r where lower(r.name) like %:name%")
    Department findByName(@Param("name") String name);


}
