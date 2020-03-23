package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.ipar.NationalIndicator;
import org.devgateway.toolkit.persistence.dao.NationalIndicator_;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Person_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class NationalIndicatorFilterState extends JpaFilterState<NationalIndicator> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String organization;

    public NationalIndicatorFilterState() {
    }

    public NationalIndicatorFilterState(String organization) {
        this.organization = organization;
    }

    @Override
    public Specification<NationalIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (organization != null) {
                Join<NationalIndicator, Person> personJoin = root.join(NationalIndicator_.UPLOADED_BY);
                Join<Person, Organization> organizationJoin = personJoin.join(Person_.ORGANIZATION);
                predicates.add(cb.like(cb.lower(organizationJoin.get(Organization_.LABEL)),
                        "%" + organization.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
