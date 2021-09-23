package org.devgateway.toolkit.persistence.service.indicator;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.BaseJpaService;


/**
 * Created by Daniel Oliva
 */
public interface IndicatorMetadataService extends BaseJpaService<IndicatorMetadata> {
    List<IndicatorType> findIndicatorTypes(Organization organization);

    IndicatorMetadata findByType(IndicatorType indicatorType);
}
