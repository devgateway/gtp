package org.devgateway.toolkit.persistence.service.cnsc.menu;

import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface CNSCHeaderService extends BaseJpaService<CNSCHeader> {

    CNSCHeader get();
}
