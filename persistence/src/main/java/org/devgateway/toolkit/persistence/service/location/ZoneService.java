package org.devgateway.toolkit.persistence.service.location;

import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;


/**
 * @author Nadejda Mandrescu
 */
public interface ZoneService extends BaseJpaService<Zone>, TextSearchableService<Zone> {
}
