package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsReferenceService extends BaseJpaService<RiverStationYearlyLevelsReference> {

    boolean exists(RiverStation station, HydrologicalYear year, Long exceptId);
}
