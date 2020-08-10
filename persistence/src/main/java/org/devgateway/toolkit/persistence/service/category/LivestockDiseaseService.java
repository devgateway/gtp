package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.UniquePropertyService;

/**
 * @author Nadejda Mandrescu
 */
public interface LivestockDiseaseService extends BaseJpaService<LivestockDisease>,
        UniquePropertyService<LivestockDisease> {
}
