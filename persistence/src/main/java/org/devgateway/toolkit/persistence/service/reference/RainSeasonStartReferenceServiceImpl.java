package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainSeasonStartReferenceRepository;
import org.devgateway.toolkit.persistence.repository.reference.YearsReferenceRepository;
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
public class RainSeasonStartReferenceServiceImpl extends YearsReferenceServiceImpl<RainSeasonStartReference>
        implements RainSeasonStartReferenceService {

    @Autowired
    private RainSeasonStartReferenceRepository rainSeasonStartReferenceRepository;

    @Override
    protected BaseJpaRepository<RainSeasonStartReference, Long> repository() {
        return rainSeasonStartReferenceRepository;
    }

    @Override
    public YearsReferenceRepository<RainSeasonStartReference> yearsReferenceRepository() {
        return rainSeasonStartReferenceRepository;
    }

    @Override
    public RainSeasonStartReference newInstance() {
        return new RainSeasonStartReference();
    }

    @Override
    protected PluviometricPostHolder addPluviometricPostReference(RainSeasonStartReference rainReference,
            PluviometricPost pluviometricPost) {
        RainSeasonPluviometricPostReferenceStart postReferenceStart =
                new RainSeasonPluviometricPostReferenceStart();
        postReferenceStart.setPluviometricPost(pluviometricPost);
        rainReference.addPostReference(postReferenceStart);
        return postReferenceStart;
    }

}
