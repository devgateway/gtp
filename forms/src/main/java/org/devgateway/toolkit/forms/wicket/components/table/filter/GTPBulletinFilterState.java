package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPBulletin_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class GTPBulletinFilterState extends JpaFilterState<GTPBulletin> {

    private Integer year;

    private Month month;

    private Decadal decadal;

    @Override
    public Specification<GTPBulletin> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (year != null) {
                predicates.add(cb.equal(root.get(GTPBulletin_.year), year));
            }

            if (month != null) {
                predicates.add(cb.equal(root.get(GTPBulletin_.month), month));
            }

            if (decadal != null) {
                predicates.add(cb.equal(root.get(GTPBulletin_.decadal), decadal));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }
}
