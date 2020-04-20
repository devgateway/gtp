package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason_;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class PluviometricPostRainSeasonFilterState extends JpaFilterState<PluviometricPostRainSeason> {
    private static final long serialVersionUID = 8682732798156199922L;

    private Integer year;

    @Override
    public Specification<PluviometricPostRainSeason> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.rainSeason).get(RainSeason_.year), year));
            predicates.add(cb.notEqual(root.get(PluviometricPostRainSeason_.deleted), true));

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
