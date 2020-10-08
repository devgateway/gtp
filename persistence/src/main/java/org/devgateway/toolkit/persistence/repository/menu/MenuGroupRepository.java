package org.devgateway.toolkit.persistence.repository.menu;

import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface MenuGroupRepository extends BaseJpaRepository<MenuGroup, Long> {

    MenuGroup findByName(String name);
}
