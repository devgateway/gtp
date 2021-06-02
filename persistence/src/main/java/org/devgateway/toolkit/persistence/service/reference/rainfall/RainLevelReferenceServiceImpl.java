package org.devgateway.toolkit.persistence.service.reference.rainfall;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.dto.rainfall.MonthDecadalRainLevel;
import org.devgateway.toolkit.persistence.dto.rainfall.ReferenceLevels;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainLevelReferenceRepository;
import org.devgateway.toolkit.persistence.repository.reference.YearsReferenceRepository;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.reference.YearsReferenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class RainLevelReferenceServiceImpl extends YearsReferenceServiceImpl<RainLevelReference>
        implements RainLevelReferenceService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RainLevelReferenceRepository rainLevelReferenceRepository;

    @Autowired
    private PluviometricPostService pluviometricPostService;

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
    public <S extends RainLevelReference> S saveAndFlush(S entity) {
        S savedEntity = super.saveAndFlush(entity);
        em.refresh(savedEntity);
        return savedEntity;
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
        return new ReferenceLevels(rlr.getYearStart(), rlr.getYearEnd(),
                rlr.getReferenceYearStart(), rlr.getReferenceYearEnd(), rainLevels);
    }

    @Override
    @Transactional(readOnly = true)
    public void export(RainLevelReference rainReference, OutputStream outputStream) throws IOException {
        RainLevelReferenceWriter writer = new RainLevelReferenceWriter(rainReference);
        writer.write(outputStream);
    }

    @Override
    public RainLevelReference getExample(Integer referenceYearStart, Integer referenceYearEnd) {
        RainLevelReference r = new RainLevelReference();
        r.setReferenceYearStart(referenceYearStart);
        r.setReferenceYearEnd(referenceYearEnd);

        pluviometricPostService
                .findAll(Sort.by(Sort.Direction.ASC, "label"))
                .forEach(pp -> {
                    RainLevelPluviometricPostReference rainPostRef = new RainLevelPluviometricPostReference();
                    rainPostRef.setPluviometricPost(pp);
                    r.addPostReference(rainPostRef);
                });
        return r;
    }
}
