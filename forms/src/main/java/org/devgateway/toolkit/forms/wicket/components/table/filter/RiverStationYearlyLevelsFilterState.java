package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationYearlyLevelsFilterState extends JpaFilterState<RiverStationYearlyLevels> {

    private HydrologicalYear year;

    @Override
    public Specification<RiverStationYearlyLevels> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get(RiverStationYearlyLevels_.year), year));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public HydrologicalYear getYear() {
        return year;
    }

    public void setYear(HydrologicalYear year) {
        this.year = year;
    }
}
