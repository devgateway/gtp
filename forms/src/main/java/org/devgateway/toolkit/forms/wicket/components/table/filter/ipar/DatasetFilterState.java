package org.devgateway.toolkit.forms.wicket.components.table.filter.ipar;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class DatasetFilterState<T extends Dataset> extends JpaFilterState<T> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String organization;
    private String label;

    public DatasetFilterState() {

    }

    public  DatasetFilterState(String organization) {
        this.organization = organization;
    }

    @Override
    public Specification<T> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(label)) {
                predicates.add(cb.like(cb.lower(root.get(Dataset_.LABEL)), "%" + label.toLowerCase() + "%"));
            }
            if (organization != null) {
                Join<Dataset, Organization> join = root.join(Dataset_.ORGANIZATION);
                predicates.add(cb.like(cb.lower(join.get(Organization_.label)),
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
