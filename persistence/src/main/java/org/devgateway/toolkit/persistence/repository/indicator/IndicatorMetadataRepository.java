package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface IndicatorMetadataRepository extends BaseJpaRepository<IndicatorMetadata, Long> {

    @CacheHibernateQueryResult
    List<IndicatorMetadata> findByOrganization(Organization organization);

    @CacheHibernateQueryResult
    IndicatorMetadata findByType(IndicatorType indicatorType);
}
