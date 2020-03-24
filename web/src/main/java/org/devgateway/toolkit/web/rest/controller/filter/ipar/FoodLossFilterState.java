package org.devgateway.toolkit.web.rest.controller.filter.ipar;

// import org.devgateway.toolkit.persistence.dao.Data_;
import org.devgateway.toolkit.persistence.dao.ipar.FoodLossIndicator;
// import org.devgateway.toolkit.persistence.dao.FoodLossIndicator_;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
// import org.devgateway.toolkit.persistence.dao.categories.CropType_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
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
                addCropTypePredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addLossTypePredicates(root, cb, predicates);
                addMinPercentagePredicate(root, cb, predicates);
                addMaxPercentagePredicate(root, cb, predicates);
                addMinKgPredicate(root, cb, predicates);
                addMaxKgPredicate(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            List<Order> orders = new ArrayList<>();
            // orders.add(new OrderImpl(root.get(Data_.YEAR), true));
            // Join<FoodLossIndicator, CropType> join = root.join(FoodLossIndicator_.CROP_TYPE, JoinType.LEFT);
            // orders.add(new OrderImpl(join.get(CropType_.LABEL), true));
            query.orderBy(orders);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    protected void addMinPercentagePredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        // addMinPredicate(root, cb, predicates, filter.getMinAvgPercentage(), FoodLossIndicator_.AVG_PERCENTAGE);
    }

    protected void addMaxPercentagePredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        // addMaxPredicate(root, cb, predicates, filter.getMaxAvgPercentage(), FoodLossIndicator_.AVG_PERCENTAGE);
    }


    protected void addMinKgPredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                     List<Predicate> predicates) {
        // addMinPredicate(root, cb, predicates, filter.getMinAvgKilogram(), FoodLossIndicator_.AVG_KILOGRAMS);
    }

    protected void addMaxKgPredicate(Root<FoodLossIndicator> root, CriteriaBuilder cb,
                                     List<Predicate> predicates) {
        // addMaxPredicate(root, cb, predicates, filter.getMaxAvgKilogram(), FoodLossIndicator_.AVG_KILOGRAMS);
    }

    protected void addLossTypePredicates(Root<FoodLossIndicator> root, CriteriaBuilder cb, List<Predicate> predicates) {
        // addIntPredicates(root, cb, predicates, filter.getLossType(), FoodLossIndicator_.LOSS_TYPE);
    }
}
