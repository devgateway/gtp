package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference_;
import org.devgateway.toolkit.persistence.time.AD3Clock;
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
public class RainLevelReferenceFilterState extends JpaFilterState<RainLevelReference> {
    private static final long serialVersionUID = -510277993753004886L;

    private static final Logger LOGGER = LoggerFactory.getLogger(RainLevelReferenceFilterState.class);

    private Integer year;

    public RainLevelReferenceFilterState() {
        this.year = LocalDate.now(AD3Clock.systemDefaultZone()).getYear();
    }

    @Override
    public Specification<RainLevelReference> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // forces
            LOGGER.debug("currentYear = " + year);

            Integer currentYear = LocalDate.now(AD3Clock.systemDefaultZone()).getYear();
            predicates.add(cb.lessThanOrEqualTo(root.get(RainLevelReference_.yearStart), currentYear));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
