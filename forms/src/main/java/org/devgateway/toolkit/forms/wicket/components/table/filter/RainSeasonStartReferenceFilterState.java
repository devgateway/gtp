package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonStartReferenceFilterState extends JpaFilterState<RainSeasonStartReference> {
    private static final long serialVersionUID = -1868082369630144875L;

    private static final Logger LOGGER = LoggerFactory.getLogger(RainSeasonStartReferenceFilterState.class);

    /**
     * The data is filtered below by current year.
     * In URL we don't have anything about year, so the filter state gets cached.
     * This always changing filter will ensure filter is rerun and show extra data if needed on year change.
     */
    private Long antiCache;

    @Override
    public Specification<RainSeasonStartReference> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // avoids filter cache
            LOGGER.debug("antiCache=" + antiCache);

            Integer currentYear = LocalDate.now().getYear();
            predicates.add(cb.lessThanOrEqualTo(root.get(RainSeasonStartReference_.yearStart), currentYear));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Long getAntiCache() {
        return antiCache;
    }

    public void setAntiCache(Long antiCache) {
        this.antiCache = antiCache;
    }
}
