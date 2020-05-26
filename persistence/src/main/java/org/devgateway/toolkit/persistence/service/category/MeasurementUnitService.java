package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

/**
 * @author Octavian Ciubotaru
 */
public interface MeasurementUnitService extends BaseJpaService<MeasurementUnit>,
        TextSearchableService<MeasurementUnit> {
}
