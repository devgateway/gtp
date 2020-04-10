package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.BaseJpaService;


/**
 * Created by Daniel Oliva
 */
public interface IndicatorMetadataService extends BaseJpaService<IndicatorMetadata> {
    IndicatorMetadata findOneByType(IndicatorType indicatorType);
}
