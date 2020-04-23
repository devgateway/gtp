package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface RainLevelReferenceService extends BaseJpaService<RainLevelReference>,
        YearsReferenceService<RainLevelReference> {
}
