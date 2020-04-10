package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface IndicatorMetadataRepository extends BaseJpaRepository<IndicatorMetadata, Long> {

    IndicatorMetadata findOneByType(IndicatorType indicatorType);

}
