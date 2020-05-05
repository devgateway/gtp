package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.repository.category.OrganizationRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author idobre
 * @since 2019-03-04
 */
@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl extends BaseJpaServiceImpl<Organization> implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    protected BaseJpaRepository<Organization, Long> repository() {
        return organizationRepository;
    }

    @Override
    public Organization newInstance() {
        return new Organization();
    }
}
