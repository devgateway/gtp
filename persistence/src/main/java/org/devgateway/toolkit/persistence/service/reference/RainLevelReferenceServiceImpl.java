package org.devgateway.toolkit.persistence.service.reference;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dao.reference.DecadalRainLevelReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainLevelReferenceRepository;
import org.devgateway.toolkit.persistence.repository.reference.YearsReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class RainLevelReferenceServiceImpl extends YearsReferenceServiceImpl<RainLevelReference>
        implements RainLevelReferenceService {

    @Autowired
    private RainLevelReferenceRepository rainLevelReferenceRepository;

    @Override
    protected BaseJpaRepository<RainLevelReference, Long> repository() {
        return rainLevelReferenceRepository;
    }

    @Override
    public RainLevelReference newInstance() {
        return new RainLevelReference();
    }

    @Override
    public YearsReferenceRepository<RainLevelReference> yearsReferenceRepository() {
        return rainLevelReferenceRepository;
    }

    @Override
    protected PluviometricPostHolder addPluviometricPostReference(RainLevelReference reference,
            PluviometricPost pluviometricPost) {
        RainLevelPluviometricPostReference postLevelReference =
                new RainLevelPluviometricPostReference();
        postLevelReference.setPluviometricPost(pluviometricPost);
        reference.addPostReference(postLevelReference);
        for (Month month : MONTHS) {
            for (Decadal decadal : Decadal.values()) {
                DecadalRainLevelReference decadalRainfall = new DecadalRainLevelReference();
                decadalRainfall.setMonth(month);
                decadalRainfall.setDecadal(decadal);
                postLevelReference.addDecadalRainReference(decadalRainfall);
            }
        }
        return postLevelReference;
    }

}
