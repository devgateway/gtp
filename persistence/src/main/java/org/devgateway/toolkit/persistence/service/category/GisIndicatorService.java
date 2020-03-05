package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.GisIndicator;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

public interface GisIndicatorService extends BaseJpaService<GisIndicator>, TextSearchableService<GisIndicator> {
}
