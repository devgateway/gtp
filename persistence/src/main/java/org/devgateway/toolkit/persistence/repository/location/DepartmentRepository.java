package org.devgateway.toolkit.persistence.repository.location;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DepartmentRepository extends TextSearchableRepository<Department, Long> {

    @Query("select r from Department r where lower(r.name) like %:name%")
    Department findByName(@Param("name") String name);

    @Override
    @Query("select d from Department d where lower(d.name) like %:code% order by d.name")
    Page<Department> searchText(String code, Pageable page);

    @Override
    @CacheHibernateQueryResult
    @Query("from Department order by name")
    List<Department> findAll();
}
