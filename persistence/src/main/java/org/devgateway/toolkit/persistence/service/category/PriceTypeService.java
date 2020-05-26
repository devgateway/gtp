package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

/**
 * @author Octavian Ciubotaru
 */
public interface PriceTypeService extends BaseJpaService<PriceType>,
        TextSearchableService<PriceType> {
}
