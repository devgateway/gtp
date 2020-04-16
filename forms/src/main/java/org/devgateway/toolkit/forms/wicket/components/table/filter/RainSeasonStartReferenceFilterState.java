package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference_;
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

    @Override
    public Specification<RainSeasonStartReference> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Integer currentYear = LocalDate.now().getYear();
            predicates.add(cb.lessThanOrEqualTo(root.get(RainSeasonStartReference_.yearEnd), currentYear));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
