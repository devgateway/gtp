package org.devgateway.toolkit.persistence.service.menu;

import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface MenuGroupService extends BaseJpaService<MenuGroup> {

    MenuGroup findMenuRoot();
}
