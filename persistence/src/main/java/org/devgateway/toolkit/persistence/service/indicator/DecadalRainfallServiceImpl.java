package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class DecadalRainfallServiceImpl extends BaseJpaServiceImpl<DecadalRainfall> implements DecadalRainfallService {
    @Autowired
    private DecadalRainfallRepository decadalRainfallRepository;

    @Override
    protected BaseJpaRepository<DecadalRainfall, Long> repository() {
        return decadalRainfallRepository;
    }

    @Override
    public DecadalRainfall newInstance() {
        return new DecadalRainfall();
    }
}
