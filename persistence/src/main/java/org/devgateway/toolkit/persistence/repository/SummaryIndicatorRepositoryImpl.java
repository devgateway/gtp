package org.devgateway.toolkit.persistence.repository;


import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator_;
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

    public List<PovertySummary> getPovertyByYearAndRegion(final Specification<PovertyIndicator> spec) {
        LOGGER.debug("getPovertyByYearAndRegion");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        Root<PovertyIndicator> root = query.from(PovertyIndicator.class);
        Path<Integer> region = root.join(PovertyIndicator_.REGION);
        Path<Integer> povertyLevel = root.join(PovertyIndicator_.POVERTY_LEVEL);

        query.multiselect(
                cb.count(root.get(PovertyIndicator_.POVERTY_LEVEL)),
                povertyLevel,
                root.get(PovertyIndicator_.YEAR),
                region
        );
        query.groupBy(
                povertyLevel,
                root.get(PovertyIndicator_.YEAR),
                region
        );
        query.orderBy(cb.asc(root.get(PovertyIndicator_.YEAR)), cb.asc(region), cb.asc(povertyLevel));

        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }
        TypedQuery q = em.createQuery(query);
        List<Object[]> allItems = q.getResultList();
        List<PovertySummary> summary = allItems.stream().map(s -> new PovertySummary(s)).collect(Collectors.toList());
        return summary;
    }

    class PovertySummary {
        private long count;
        private String povertyLevel;
        private String povertyLevelFr;
        private String region;
        private int year;

        PovertySummary(Object[] objects) {
            if (objects != null && objects.length > 3) {
                this.count = (long) objects[0];
                this.povertyLevel = objects[1].toString();
                this.year = (int) objects[2];
                this.region = objects[3].toString();
            }
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public String getPovertyLevel() {
            return povertyLevel;
        }

        public void setPovertyLevel(String povertyLevel) {
            this.povertyLevel = povertyLevel;
        }

        public String getPovertyLevelFr() {
            return povertyLevelFr;
        }

        public void setPovertyLevelFr(String povertyLevelFr) {
            this.povertyLevelFr = povertyLevelFr;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
