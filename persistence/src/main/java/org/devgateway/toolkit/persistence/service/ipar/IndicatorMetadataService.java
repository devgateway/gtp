package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.BaseJpaService;


/**
 * Created by Daniel Oliva
 */
public interface IndicatorMetadataService extends BaseJpaService<IndicatorMetadata> {

    IndicatorMetadata findByIndicatorType(Integer type);

}
