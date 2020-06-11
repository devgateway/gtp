package org.devgateway.toolkit.persistence.service.category;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface MarketService extends BaseJpaService<Market> {

    boolean exists(Department department, MarketType marketType, String name, Long exceptId);

    boolean exists(Double latitude, Double longitude, Long id);

    List<Market> findByMarketTypeName(String marketTypeName);
}
