package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class DatasetFilterState extends JpaFilterState<Dataset> {

    private static final long serialVersionUID = 8005371716983257722L;
    private Organization organization;

    public DatasetFilterState(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Specification<Dataset> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (organization != null) {
                predicates.add(cb.or(
                    cb.equal(root.get(Dataset_.organization), organization),
                    cb.isTrue(root.get(Dataset_.approved))
                ));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
