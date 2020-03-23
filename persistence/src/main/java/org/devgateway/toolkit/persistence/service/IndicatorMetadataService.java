package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;


/**
 * Created by Daniel Oliva
 */
public interface IndicatorMetadataService extends BaseJpaService<IndicatorMetadata> {

    IndicatorMetadata findByIndicatorType(Integer type);

}
