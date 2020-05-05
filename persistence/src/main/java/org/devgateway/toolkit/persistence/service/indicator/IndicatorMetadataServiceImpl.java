package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.indicator.IndicatorMetadataRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Service
@Transactional(readOnly = true)
public class IndicatorMetadataServiceImpl extends BaseJpaServiceImpl<IndicatorMetadata>
        implements IndicatorMetadataService {

    @Autowired
    private IndicatorMetadataRepository repository;

    @Override
    protected BaseJpaRepository<IndicatorMetadata, Long> repository() {
        return repository;
    }

    @Override
    public IndicatorMetadata newInstance() {
        return new IndicatorMetadata();
    }

    @Override
    public IndicatorMetadata findOneByType(IndicatorType indicatorType) {
        return repository.findOneByType(indicatorType);
    }
}
