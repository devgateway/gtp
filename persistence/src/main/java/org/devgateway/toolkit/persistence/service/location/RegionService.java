package org.devgateway.toolkit.persistence.service.location;

import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

/**
 * @author Octavian Ciubotaru
 */
public interface RegionService extends BaseJpaService<Region>, TextSearchableService<Region> {

    Region findByName(String name);

    Region findByCode(String code);
}
