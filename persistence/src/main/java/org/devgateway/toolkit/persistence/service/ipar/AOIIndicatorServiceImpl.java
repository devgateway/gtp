package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.repository.ipar.AOIIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class AOIIndicatorServiceImpl extends AbstractDatasetServiceImpl<AgricultureOrientationIndexIndicator>
        implements AOIIndicatorService {

    @Autowired
    private AOIIndicatorRepository repository;

    @Override
    protected AuditedEntityRepository<AgricultureOrientationIndexIndicator> repository() {
        return repository;
    }

    @Override
    public AgricultureOrientationIndexIndicator newInstance() {
        return new AgricultureOrientationIndexIndicator();
    }
}
