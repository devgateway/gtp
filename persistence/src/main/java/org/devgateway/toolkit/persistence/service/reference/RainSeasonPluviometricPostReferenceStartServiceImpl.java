package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainSeasonPluviometricPostReferenceStartRepository;
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
public class RainSeasonPluviometricPostReferenceStartServiceImpl
        extends BaseJpaServiceImpl<RainSeasonPluviometricPostReferenceStart>
        implements RainSeasonPluviometricPostReferenceStartService {

    @Autowired
    private RainSeasonPluviometricPostReferenceStartRepository rainSeasonPluviometricPostReferenceStartRepository;

    @Override
    protected BaseJpaRepository<RainSeasonPluviometricPostReferenceStart, Long> repository() {
        return rainSeasonPluviometricPostReferenceStartRepository;
    }

    @Override
    public RainSeasonPluviometricPostReferenceStart newInstance() {
        return new RainSeasonPluviometricPostReferenceStart();
    }
}
