package org.devgateway.toolkit.persistence.repository.location;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "servicesCache")
@Transactional
public interface DepartmentRepository extends TextSearchableRepository<Department, Long> {

    @Cacheable
    @Query("select r from Department r where lower(r.name) like %:name%")
    Department findByName(@Param("name") String name);

    @Override
    @Query("select o from Region o where lower(o.name) like %?1%")
    Page<Department> searchText(String code, Pageable page);

}
