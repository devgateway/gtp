package org.devgateway.toolkit.persistence.service.cnsc.menu;

import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCMenuGroup;
import org.devgateway.toolkit.persistence.repository.cnsc.menu.CNSCMenuGroupRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class CNSCMenuGroupServiceImpl extends BaseJpaServiceImpl<CNSCMenuGroup> implements CNSCMenuGroupService {

    private CNSCMenuGroupRepository repository;

    @Override
    protected BaseJpaRepository<CNSCMenuGroup, Long> repository() {
        return repository;
    }

    @Override
    public CNSCMenuGroup findMenuRoot() {
        return repository.findByName(CNSCMenuGroup.ROOT);
    }

    @Override
    public CNSCMenuGroup newInstance() {
        return new CNSCMenuGroup();
    }
}
