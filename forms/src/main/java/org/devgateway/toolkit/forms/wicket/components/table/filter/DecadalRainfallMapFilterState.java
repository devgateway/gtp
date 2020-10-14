package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallMapFilterState extends JpaFilterState<DecadalRainfallMap> {
    private static final long serialVersionUID = 7743542819444105886L;

    private Integer year;

    @Override
    public Specification<DecadalRainfallMap> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get(DecadalRainfallMap_.year), year));

            // It is a requirement to show future decadals as well, not filtering them out

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
