package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.dao.Production_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class ProductionFilterState extends DataFilterState<Production> {
    private static final long serialVersionUID = 8005371716983257722L;

    private ProductionFilterPagingRequest filter;

    public ProductionFilterState(ProductionFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<Production> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addRegionPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addCropTypePredicates(root, cb, predicates);
                addApprovedDatasets(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addRegionPredicates(Root<Production> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getRegion(), Production_.REGION); // TODO fix type mismatch
    }

    protected void addCropTypePredicates(Root<Production> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getCrop(), Production_.CROP_TYPE); // TODO fix type mismatch
    }

    protected void addYearPredicates(Root<Production> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getYear(), Production_.YEAR); // TODO fix type mismatch
    }
}
