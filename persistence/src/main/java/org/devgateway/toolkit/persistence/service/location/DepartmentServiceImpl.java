package org.devgateway.toolkit.persistence.service.location;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.location.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
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

    @Override
    public JpaRepository<Department, Long> getRepository() {
        return departmentRepository;
    }

    @Override
    public Page<Department> searchText(String term, Pageable page) {
        return departmentRepository.searchText(term, page);
    }

    @Override
    public boolean exists(String name, Long exceptId) {
        return ServiceUtil.exists(departmentRepository.findAllNames(), name, exceptId);
    }
}
