package org.devgateway.toolkit.persistence.repository.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainLevelReferenceRepository extends YearsReferenceRepository<RainLevelReference> {
}
