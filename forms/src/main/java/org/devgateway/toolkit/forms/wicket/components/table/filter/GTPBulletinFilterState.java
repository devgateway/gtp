package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class GTPBulletinFilterState extends JpaFilterState<GTPBulletin> {
    private static final long serialVersionUID = -5430089691735799844L;

    private Integer year;

    private Month month;

    private Decadal decadal;

    private Long departmentId;

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

            if (departmentId == null) {
                predicates.add(cb.isNull(root.get(GTPBulletin_.department)));
            } else {
                predicates.add(cb.equal(root.get(GTPBulletin_.department), departmentId));
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
