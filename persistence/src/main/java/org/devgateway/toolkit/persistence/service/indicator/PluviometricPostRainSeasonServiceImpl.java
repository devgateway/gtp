package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.repository.indicator.PluviometricPostRainSeasonRepository;
import org.devgateway.toolkit.persistence.repository.indicator.RainSeasonRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.PluviometricPostStatus;
import org.devgateway.toolkit.persistence.status.RainSeasonYearProgress;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class PluviometricPostRainSeasonServiceImpl extends BaseJpaServiceImpl<PluviometricPostRainSeason>
    implements PluviometricPostRainSeasonService {

    @Autowired
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    @Autowired
    private PluviometricPostRainSeasonRepository pluviometricPostRainSeasonRepository;

    @Autowired
    private RainSeasonRepository rainSeasonRepository;

    @Override
    protected BaseJpaRepository<PluviometricPostRainSeason, Long> repository() {
        return pluviometricPostRainSeasonRepository;
    }

    @Override
    public PluviometricPostRainSeason newInstance() {
        return new PluviometricPostRainSeason();
    }

    @Override
    public boolean existsByYear(Integer year) {
        // forcing generate() workflow for more complex checks
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public void generate(Integer year) {
        Set<PluviometricPost> allowed = getPostsWithReferenceData(year);

        RainSeason rainSeason = rainSeasonRepository.findByYear(year).orElseGet(RainSeason::new);
        boolean hasChanges = rainSeason.isNew();
        rainSeason.setYear(year);
        for (PluviometricPostRainSeason pluviometricPostRainSeason : rainSeason.getPostRainSeasons()) {
            if (allowed.contains(pluviometricPostRainSeason.getPluviometricPost())) {
                allowed.remove(pluviometricPostRainSeason.getPluviometricPost());
                if (pluviometricPostRainSeason.isDeleted()) {
                    pluviometricPostRainSeason.setDeleted(false);
                    hasChanges = true;
                }
            } else if (!pluviometricPostRainSeason.isDeleted()) {
                pluviometricPostRainSeason.setDeleted(true);
                hasChanges = true;
            }
        }
        if (!allowed.isEmpty()) {
            hasChanges = true;
            allowed.forEach(pluviometricPost -> {
                PluviometricPostRainSeason pluviometricPostRainSeason = new PluviometricPostRainSeason();
                pluviometricPostRainSeason.setPluviometricPost(pluviometricPost);
                rainSeason.addPostRainSeason(pluviometricPostRainSeason);
            });
        }
        if (hasChanges) {
            rainSeasonRepository.saveAndFlush(rainSeason);
        }
    }

    @Override
    public RainSeasonYearProgress getProgress(Integer year) {
        Set<PluviometricPost> postsWithReferenceData = getPostsWithReferenceData();

        Optional<RainSeason> optionalRainSeason = rainSeasonRepository.findByYear(year);

        Map<PluviometricPost, DataEntryStatus> statusByPost = new HashMap<>();
        if (optionalRainSeason.isPresent()) {
            for (PluviometricPostRainSeason pprs : optionalRainSeason.get().getPostRainSeasons()) {
                if (!pprs.isDeleted()) {
                    if (pprs.getFormStatus() == FormStatus.DRAFT) {
                        statusByPost.put(pprs.getPluviometricPost(), DataEntryStatus.DRAFT);
                    }
                    if (pprs.getFormStatus() == FormStatus.PUBLISHED) {
                        statusByPost.put(pprs.getPluviometricPost(), DataEntryStatus.PUBLISHED);
                    }
                }
            }
        }

        return new RainSeasonYearProgress(
                postsWithReferenceData.stream()
                        .map(p -> new PluviometricPostStatus(p, statusByPost.getOrDefault(p, DataEntryStatus.NO_DATA)))
                        .collect(Collectors.toList()));
    }

    private Set<PluviometricPost> getPostsWithReferenceData(Integer year) {
        RainSeasonStartReference reference = rainSeasonStartReferenceService
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(year);
        if (reference == null) {
            return Collections.emptySet();
        }
        return reference.getPostReferences().stream()
                .filter(postReference -> postReference.getStartReference() != null)
                .map(RainSeasonPluviometricPostReferenceStart::getPluviometricPost)
                .collect(Collectors.toSet());
    }

    private Set<PluviometricPost> getPostsWithReferenceData() {
        List<RainSeasonStartReference> references = rainSeasonStartReferenceService.findAll();
        return references.stream().flatMap(l -> l.getPostReferences().stream())
                .filter(postReference -> postReference.getStartReference() != null)
                .map(RainSeasonPluviometricPostReferenceStart::getPluviometricPost)
                .collect(Collectors.toSet());
    }
}
