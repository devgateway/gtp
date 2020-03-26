package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator_;
import org.devgateway.toolkit.persistence.dao.Data_;
import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.dao.categories.IndexType_;
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
public class AOIFilterState extends DataFilterState<AgricultureOrientationIndexIndicator> {
    private static final long serialVersionUID = 8005371716983257722L;

    private AOIFilterPagingRequest filter;

    public AOIFilterState(AOIFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<AgricultureOrientationIndexIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addIndexTypePredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            List<Order> orders = new ArrayList<>();
            orders.add(new OrderImpl(root.get(Data_.YEAR), true));
            Join<AgricultureOrientationIndexIndicator, IndexType> join =
                    root.join(AgricultureOrientationIndexIndicator_.INDEX_TYPE, JoinType.LEFT);
            orders.add(new OrderImpl(join.get(IndexType_.LABEL), true));
            query.orderBy(orders);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addIndexTypePredicates(Root<AgricultureOrientationIndexIndicator> root,
                                          CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getIndexType(), AgricultureOrientationIndexIndicator_.INDEX_TYPE);
    }
}
