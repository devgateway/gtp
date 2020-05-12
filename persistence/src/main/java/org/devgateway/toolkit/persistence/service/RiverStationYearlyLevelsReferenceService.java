package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;

/**
 * @author Octavian Ciubotaru
 */
public interface RiverStationYearlyLevelsReferenceService extends BaseJpaService<RiverStationYearlyLevelsReference> {

    boolean exists(RiverStation station, HydrologicalYear year, Long exceptId);
}
