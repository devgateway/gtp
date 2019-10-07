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
                addCropTypePredicates(root, cb, predicates, filter.getCrop(), FoodLossIndicator_.CROP_TYPE);
                addYearPredicates(root, cb, predicates, filter.getYear(), FoodLossIndicator_.YEAR);
                addLossTypePredicates(root, cb, predicates);
                addMinPercentagePredicate(root, cb, predicates);
                addMaxPercentagePredicate(root, cb, predicates);
                addMinKgPredicate(root, cb, predicates);
                addMaxKgPredicate(root, cb, predicates);
                addApprovedDatasets(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    protected void addMinPercentagePredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        addMinPredicate(root, cb, predicates, filter.getMinPercentage(), FoodLossIndicator_.AVG_PERCENTAGE);
    }

    protected void addMaxPercentagePredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        addMaxPredicate(root, cb, predicates, filter.getMaxPercentage(), FoodLossIndicator_.AVG_PERCENTAGE);
    }


    protected void addMinKgPredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        addMinPredicate(root, cb, predicates, filter.getMinKg(), FoodLossIndicator_.AVG_KILOGRAMS);
    }

    protected void addMaxKgPredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        addMaxPredicate(root, cb, predicates, filter.getMaxKg(), FoodLossIndicator_.AVG_KILOGRAMS);
    }

    protected void addLossTypePredicates(Root<FoodLossIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getLossType(), FoodLossIndicator_.LOSS_TYPE);
        // TODO fix type mismatch
    }
}