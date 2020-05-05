package org.devgateway.toolkit.persistence.service.reference;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dto.rainfall.MonthDecadalRainLevel;
import org.devgateway.toolkit.persistence.dto.rainfall.ReferenceLevels;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainLevelReferenceRepository;
import org.devgateway.toolkit.persistence.repository.reference.YearsReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Service
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
                RainLevelMonthReference monthDecadalRainfall = new RainLevelMonthReference();
                monthDecadalRainfall.setMonth(month);
                monthDecadalRainfall.setDecadal(decadal);
                postLevelReference.addRainLevelMonthReference(monthDecadalRainfall);
            }
        }
        return postLevelReference;
    }

    @Override
    public List<ReferenceLevels> findReferenceLevels(Collection<Integer> years, Long pluviometricPostId) {
        return findApplicableReferences(years).stream()
                .map(rlr -> findReferenceLevels(rlr, pluviometricPostId))
                .collect(Collectors.toList());
    }

    private List<RainLevelReference> findApplicableReferences(Collection<Integer> years) {
        List<RainLevelReference> rlRefs = new ArrayList<>();
        for (Integer year : years) {
            if (rlRefs.stream().noneMatch(r -> r.isApplicableTo(year))) {
                rlRefs.add(findByYearStartLessThanEqualAndYearEndGreaterThanEqual(year));
            }
        }
        return rlRefs;
    }

    private ReferenceLevels findReferenceLevels(RainLevelReference rlr, Long pluviometricPostId) {
        List<MonthDecadalRainLevel> rainLevels = rainLevelReferenceRepository.findRainLevels(rlr, pluviometricPostId);
        return new ReferenceLevels(rlr.getReferenceYearStart(), rlr.getReferenceYearEnd(), rainLevels);
    }
}
