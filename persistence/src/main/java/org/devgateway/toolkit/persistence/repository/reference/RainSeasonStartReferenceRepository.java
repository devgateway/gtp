package org.devgateway.toolkit.persistence.repository.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonStartReferenceRepository extends YearsReferenceRepository<RainSeasonStartReference> {
}
