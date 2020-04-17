package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainSeasonStartReferenceRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class RainSeasonStartReferenceServiceImpl extends BaseJpaServiceImpl<RainSeasonStartReference>
        implements RainSeasonStartReferenceService {

    @Autowired
    private RainSeasonStartReferenceRepository rainSeasonStartReferenceRepository;

    @Autowired
    private PluviometricPostService pluviometricPostService;

    @Override
    protected BaseJpaRepository<RainSeasonStartReference, Long> repository() {
        return rainSeasonStartReferenceRepository;
    }

    @Override
    public RainSeasonStartReference newInstance() {
        return new RainSeasonStartReference();
    }

    @Override
    @Transactional(readOnly = false)
    public void initialize(RainSeasonStartReference rainReference) {
        List<PluviometricPost> missing = new ArrayList<>();
        if (rainReference.getPostReferences().isEmpty()) {
            missing = pluviometricPostService.findAll();
        } else {
            List<Long> existing = rainReference.getPostReferences()
                    .stream()
                    .map(ref -> ref.getPluviometricPost().getId())
                    .collect(Collectors.toList());
            missing = pluviometricPostService.findAllByIdNotIn(existing);
        }
        if (!missing.isEmpty()) {
            List<RainSeasonPluviometricPostReferenceStart> postsRefStart = missing.stream().map(pluviometricPost -> {
                RainSeasonPluviometricPostReferenceStart postReferenceStart =
                        new RainSeasonPluviometricPostReferenceStart();
                postReferenceStart.setPluviometricPost(pluviometricPost);
                postReferenceStart.setRainSeasonStartReference(rainReference);
                return postReferenceStart;
            }).collect(Collectors.toList());
            rainSeasonStartReferenceRepository.save(rainReference);
        }
    }
}
