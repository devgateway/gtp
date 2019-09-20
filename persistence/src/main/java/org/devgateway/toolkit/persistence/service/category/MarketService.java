package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface MarketService extends BaseJpaService<Market> {

    Market findByName(String departmentName, String marketName);
}
