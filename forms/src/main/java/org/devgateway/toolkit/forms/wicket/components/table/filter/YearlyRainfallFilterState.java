package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class YearlyRainfallFilterState extends JpaFilterState<YearlyRainfall> {
    private static final long serialVersionUID = 5060772257370599212L;

    private Integer year;

    public YearlyRainfallFilterState() {
    }

    @Override
    public Specification<YearlyRainfall> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (year != null) {
                predicates.add(cb.equal(root.get(YearlyRainfall_.year), year));
            }

            query.orderBy(cb.asc(root.get(YearlyRainfall_.year)));

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
