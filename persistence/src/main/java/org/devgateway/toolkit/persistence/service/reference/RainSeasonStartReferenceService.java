package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface RainSeasonStartReferenceService extends BaseJpaService<RainSeasonStartReference> {
    void initialize(RainSeasonStartReference rainReference);

    RainSeasonStartReference findByYearStartLessThanEqualAndYearEndGreaterThanEqual(Integer year);
}
