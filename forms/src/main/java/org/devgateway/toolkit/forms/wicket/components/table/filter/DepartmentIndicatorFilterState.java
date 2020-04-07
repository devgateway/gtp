package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Person_;
import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dao.DepartmentIndicator_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class DepartmentIndicatorFilterState extends JpaFilterState<DepartmentIndicator> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String reducedNameFr;
    private String reducedName;
    private String organization;

    public DepartmentIndicatorFilterState() {
    }

    public DepartmentIndicatorFilterState(String organization) {
        this.organization = organization;
    }

    @Override
    public Specification<DepartmentIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(reducedName)) {
                predicates.add(cb.like(cb.lower(root.get(DepartmentIndicator_.NAME)),
                        "%" + reducedName.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(reducedNameFr)) {
                predicates.add(cb.like(cb.lower(root.get(DepartmentIndicator_.NAME_FR)), "%"
                        + reducedNameFr.toLowerCase() + "%"));
            }
            if (organization != null) {
                Join<DepartmentIndicator, Person> personJoin = root.join(DepartmentIndicator_.UPLOADED_BY);
                Join<Person, Organization> organizationJoin = personJoin.join(Person_.ORGANIZATION);
                predicates.add(cb.like(cb.lower(organizationJoin.get(Organization_.label)),
                        "%" + organization.toLowerCase() + "%"));
            }
            query.orderBy(new OrderImpl(root.get(DepartmentIndicator_.NAME_FR), true));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getReducedNameFr() {
        return reducedNameFr;
    }

    public void setReducedNameFr(String reducedNameFr) {
        this.reducedNameFr = reducedNameFr;
    }

    public String getReducedName() {
        return reducedName;
    }

    public void setReducedName(String reducedName) {
        this.reducedName = reducedName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
