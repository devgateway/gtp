package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface RegionService extends BaseJpaService<Region> {

    Region findByName(String name);

    Region findByCode(String code);
}
