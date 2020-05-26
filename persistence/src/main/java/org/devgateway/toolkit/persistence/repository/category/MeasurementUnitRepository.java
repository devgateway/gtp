package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface MeasurementUnitRepository extends CategoryRepository<MeasurementUnit>,
        TextSearchableRepository<MeasurementUnit, Long> {
}
