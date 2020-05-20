package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.River;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.devgateway.toolkit.persistence.service.UniquePropertyService;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverService extends BaseJpaService<River>, UniquePropertyService<River>,
        TextSearchableService<River> {
}
