package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class AgriculturalWomenFilterState extends DataFilterState<AgriculturalWomenIndicator> {
    private static final long serialVersionUID = 8005371716983257722L;

    private AgriculturalWomenFilterPagingRequest filter;

    public AgriculturalWomenFilterState(AgriculturalWomenFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<AgriculturalWomenIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addGenderPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addAwGroupPredicates(root, cb, predicates);
                addAwGroupTypePredicates(root, cb, predicates);
                addApprovedDatasets(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addAwGroupPredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                       List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getAwGroup(), AgriculturalWomenIndicator_.GROUP);
        // TODO fix type mismatch
    }

    protected void addAwGroupTypePredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                        List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getAwGroupType(), AgriculturalWomenIndicator_.GROUP_TYPE);
        // TODO fix type mismatch
    }

    protected void addGenderPredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                       List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getGender(), AgriculturalWomenIndicator_.GENDER);
        // TODO fix type mismatch
    }

    protected void addYearPredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                     List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getYear(), AgriculturalWomenIndicator_.YEAR);
        // TODO fix type mismatch
    }
}
