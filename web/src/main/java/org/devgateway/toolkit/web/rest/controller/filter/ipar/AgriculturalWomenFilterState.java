package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;
// import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator_;
// import org.devgateway.toolkit.persistence.dao.Data_;
import org.devgateway.toolkit.persistence.dao.ipar.categories.AgriculturalWomenGroup;
// import org.devgateway.toolkit.persistence.dao.ipar.categories.AgriculturalWomenGroup_;
import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender;
// import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender_;
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
                addMinPercentagePredicate(root, cb, predicates);
                addMaxPercentagePredicate(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            List<Order> orders = new ArrayList<>();
            /*
            orders.add(new OrderImpl(root.get(Data_.YEAR), true));
            Join<AgriculturalWomenIndicator, Gender> join =
                    root.join(AgriculturalWomenIndicator_.GENDER, JoinType.LEFT);
            orders.add(new OrderImpl(join.get(Gender_.LABEL), true));
            Join<AgriculturalWomenIndicator, AgriculturalWomenGroup> join2 =
                    root.join(AgriculturalWomenIndicator_.GROUP, JoinType.LEFT);
            orders.add(new OrderImpl(join2.get(AgriculturalWomenGroup_.LABEL), true));
             */
            query.orderBy(orders);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    protected void addMinPercentagePredicate(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        // addMinPredicate(root, cb, predicates, filter.getMinPercentage(), AgriculturalWomenIndicator_.PERCENTAGE);
    }

    protected void addMaxPercentagePredicate(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        // addMaxPredicate(root, cb, predicates, filter.getMaxPercentage(), AgriculturalWomenIndicator_.PERCENTAGE);
    }

    protected void addAwGroupPredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                       List<Predicate> predicates) {
        // addIntPredicates(root, cb, predicates, filter.getAwGroup(), AgriculturalWomenIndicator_.GROUP);
        // TODO fix type mismatch
    }

    protected void addAwGroupTypePredicates(Root<AgriculturalWomenIndicator> root, CriteriaBuilder cb,
                                        List<Predicate> predicates) {
        // addIntPredicates(root, cb, predicates, filter.getAwGroupType(), AgriculturalWomenIndicator_.GROUP_TYPE);
        // TODO fix type mismatch
    }

}