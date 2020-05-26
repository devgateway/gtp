package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

/**
 * @author Octavian Ciubotaru
 */
public interface MarketTypeService extends TextSearchableService<MarketType>, BaseJpaService<MarketType> {
}
