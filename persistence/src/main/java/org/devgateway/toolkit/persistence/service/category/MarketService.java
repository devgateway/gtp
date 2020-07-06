package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface MarketService extends BaseJpaService<Market> {

    boolean exists(Department department, MarketType marketType, String name, Long exceptId);

    boolean exists(Double latitude, Double longitude, Long id);

    List<Market> findByMarketType(MarketType marketType);

    boolean hasProductOrQuantities(Long marketId);
}
