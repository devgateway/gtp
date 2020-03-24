package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.Department;
import org.devgateway.toolkit.persistence.repository.ipar.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
// @Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class DepartmentServiceImpl extends BaseJpaServiceImpl<Department> implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    protected BaseJpaRepository<Department, Long> repository() {
        return departmentRepository;
    }

    @Override
    public Department newInstance() {
        return new Department();
    }
}
