package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference_;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationYearlyLevelsReferenceFilterState extends JpaFilterState<RiverStationYearlyLevelsReference> {

    private String station;

    @Override
    public Specification<RiverStationYearlyLevelsReference> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (station != null) {
                predicates.add(cb.equal(root.get(RiverStationYearlyLevelsReference_.station)
                        .get(RiverStation_.name), station));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
