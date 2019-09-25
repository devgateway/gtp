package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class PovertyFilterState extends DataFilterState<PovertyIndicator> {
    private static final long serialVersionUID = 8005371716983257722L;

    private PovertyFilterPagingRequest filter;

    public PovertyFilterState(PovertyFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<PovertyIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addRegionPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addGenderPredicates(root, cb, predicates);
                addApprovedDatasets(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addRegionPredicates(Root<PovertyIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getRegion(), PovertyIndicator_.REGION); // TODO fix type mismatch
    }

    protected void addGenderPredicates(Root<PovertyIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getGender(), PovertyIndicator_.GENDER); // TODO fix type mismatch
    }

    protected void addYearPredicates(Root<PovertyIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getYear(), PovertyIndicator_.YEAR); // TODO fix type mismatch
    }
}
