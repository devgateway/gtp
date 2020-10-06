package org.devgateway.toolkit.persistence.service.cnsc.menu;

import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCMenuGroup;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface CNSCMenuGroupService extends BaseJpaService<CNSCMenuGroup> {

    CNSCMenuGroup findMenuRoot();
}
