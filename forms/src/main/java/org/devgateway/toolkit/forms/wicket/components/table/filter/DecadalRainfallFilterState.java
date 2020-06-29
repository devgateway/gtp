package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall_;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallFilterState extends JpaFilterState<DecadalRainfall> {
    private static final long serialVersionUID = 5060772257370599212L;

    private Integer year;

    @Override
    public Specification<DecadalRainfall> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get(DecadalRainfall_.year), year));

            LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
            if (year.equals(now.getYear())) {
                Decadal currentDecadal = Decadal.fromDayOfMonth(now.getDayOfMonth());
                predicates.add(cb.or(
                        cb.lessThan(root.get(DecadalRainfall_.month), now.getMonth()),
                        cb.and(
                                cb.equal(root.get(DecadalRainfall_.month), now.getMonth()),
                                cb.lessThan(root.get(DecadalRainfall_.decadal), currentDecadal))));
            }

            query.orderBy(cb.asc(root.get(DecadalRainfall_.month)), cb.asc(root.get(DecadalRainfall_.decadal)));

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
