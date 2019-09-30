package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class FoodLossFilterState extends DataFilterState<FoodLossIndicator> {
    private static final long serialVersionUID = 8005371716983257722L;

    private FoodLossFilterPagingRequest filter;

    public FoodLossFilterState(FoodLossFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<FoodLossIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addCropPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addLossTypePredicates(root, cb, predicates);
                addApprovedDatasets(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addYearPredicates(Root<FoodLossIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getYear(), FoodLossIndicator_.YEAR); // TODO fix type mismatch
    }

    protected void addCropPredicates(Root<FoodLossIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getCrop(), FoodLossIndicator_.CROP_TYPE);
        // TODO fix type mismatch
    }

    protected void addLossTypePredicates(Root<FoodLossIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getLossType(), FoodLossIndicator_.LOSS_TYPE);
        // TODO fix type mismatch
    }
}
