package org.devgateway.toolkit.persistence.repository;


import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator_;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.Region_;
import org.devgateway.toolkit.persistence.dto.AOISummary;
import org.devgateway.toolkit.persistence.dto.AgriculturalWomenSummary;
import org.devgateway.toolkit.persistence.dto.FoodLossSummary;
import org.devgateway.toolkit.persistence.dto.PovertySummary;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by dbianco
 */
@Transactional
@Service
public class SummaryIndicatorRepositoryImpl implements SummaryIndicatorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryIndicatorRepositoryImpl.class);

    @Autowired
    private EntityManager em;

    @Autowired
    private AgriculturalWomenIndicatorService womenService;

    @Autowired
    private AOIIndicatorService aoiService;

    @Autowired
    private FoodLossIndicatorService foodLossService;

    public List<PovertySummary> getPovertyByYearAndRegionAndLevel(final Specification<PovertyIndicator> spec) {
        LOGGER.debug("getPovertyByYearAndRegion");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        Root<PovertyIndicator> root = query.from(PovertyIndicator.class);
        Path<Integer> region = root.join(PovertyIndicator_.REGION);
        Path<String> regionName = region.get(Region_.NAME);
        Path<Integer> povertyLevel = root.join(PovertyIndicator_.POVERTY_LEVEL);

        query.multiselect(
                cb.count(root.get(PovertyIndicator_.POVERTY_LEVEL)),
                povertyLevel,
                root.get(PovertyIndicator_.YEAR),
                region
        );

        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }

        query.groupBy(
                povertyLevel,
                root.get(PovertyIndicator_.YEAR),
                regionName,
                region
        );
        query.orderBy(
                cb.asc(regionName),
                cb.asc(root.get(PovertyIndicator_.YEAR)),
                cb.asc(region),
                cb.asc(povertyLevel));

        TypedQuery q = em.createQuery(query);
        List<Object[]> allItems = q.getResultList();
        List<PovertySummary> summary = allItems.stream().map(s -> new PovertySummary(s)).collect(Collectors.toList());
        calculatePercentage(summary, spec);
        return summary;
    }

    private void calculatePercentage(List<PovertySummary> summary, final Specification<PovertyIndicator> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        Root<PovertyIndicator> root = query.from(PovertyIndicator.class);
        Path<Integer> region = root.join(PovertyIndicator_.REGION);

        query.multiselect(
                cb.count(root.get(PovertyIndicator_.POVERTY_LEVEL)),
                root.get(PovertyIndicator_.YEAR),
                region
        );
        query.groupBy(
                root.get(PovertyIndicator_.YEAR),
                region
        );
        query.orderBy(cb.asc(root.get(PovertyIndicator_.YEAR)), cb.asc(region));

        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }
        TypedQuery q = em.createQuery(query);
        List<Object[]> allItems = q.getResultList();
        summary.stream().forEach(s -> {
            Optional<Object[]> total = allItems.stream()
                    .filter(obj -> (int) obj[1] == s.getYear() && ((Region) obj[2]).getId().equals(s.getRegionId()))
                    .findAny();
            if (total.isPresent()) {
                s.setPercentage(new Double(s.getCount()) / new Double((Long) total.get()[0]));
            }
        });
    }

    public List<AgriculturalWomenSummary> getAgriculturalWomenIndicator(
            final Specification<AgriculturalWomenIndicator> spec) {
        List<AgriculturalWomenIndicator> womenIndicators = womenService.findAll(spec);
        return womenIndicators.stream().map(w -> new AgriculturalWomenSummary(w)).collect(Collectors.toList());
    }

    @Override
    public List<AOISummary> getAOIIndicator(Specification<AgricultureOrientationIndexIndicator> spec) {
        List<AgricultureOrientationIndexIndicator> aoiIndicators = aoiService.findAll(spec);
        return aoiIndicators.stream().map(a -> new AOISummary(a)).collect(Collectors.toList());
    }

    @Override
    public List<FoodLossSummary> getFoodLossIndicator(Specification<FoodLossIndicator> spec) {
        List<FoodLossIndicator> foodIndicators = foodLossService.findAll(spec);
        return foodIndicators.stream().map(f -> new FoodLossSummary(f)).collect(Collectors.toList());
    }
}
