package org.devgateway.toolkit.persistence.service.menu;

import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.repository.menu.MenuGroupRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class MenuGroupServiceImpl extends BaseJpaServiceImpl<MenuGroup> implements MenuGroupService {

    private MenuGroupRepository repository;

    @Override
    protected BaseJpaRepository<MenuGroup, Long> repository() {
        return repository;
    }

    @Override
    public MenuGroup findMenuRoot() {
        return repository.findByName(MenuGroup.ROOT);
    }

    @Override
    public MenuGroup newInstance() {
        return new MenuGroup();
    }
}
