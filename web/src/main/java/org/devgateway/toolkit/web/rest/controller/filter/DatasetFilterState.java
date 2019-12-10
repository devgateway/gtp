package org.devgateway.toolkit.web.rest.controller.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class DatasetFilterState {
    private static final long serialVersionUID = 8005371716983257722L;

    private DatasetFilterPagingRequest filter;

    public DatasetFilterState(DatasetFilterPagingRequest filter) {
        this.filter = filter;
    }

    public Specification<Dataset> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDatePredicates(root, cb, predicates);
                addTextPredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            query.orderBy(new OrderImpl(root.get(Dataset_.CREATED_DATE), false));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addApprovedDatasets(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        predicates.add(cb.and(cb.isTrue(root.get(Dataset_.APPROVED))));
    }

    protected void addTextPredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (StringUtils.isNotBlank(filter.getText())) {
            String txt = filter.getText().toLowerCase();
            Join<Dataset, Organization> join = root.join(Dataset_.ORGANIZATION, JoinType.LEFT);
            predicates.add(cb.and(cb.or(
                    cb.like(cb.lower(root.get(Dataset_.LABEL)), "%" + txt + "%"),
                    cb.like(cb.lower(root.get(Dataset_.SOURCE)), "%" + txt + "%"),
                    cb.like(cb.lower(join.get(Organization_.LABEL)), "%" + txt + "%"),
                    cb.like(cb.lower(root.get(Dataset_.CREATED_BY)), "%" + txt + "%")
            )));
        }
    }


    protected void addDatePredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getMinDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Dataset_.CREATED_DATE), filter.getMinDate()));
        }
        if (filter.getMaxDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Dataset_.CREATED_DATE), filter.getMaxDate()));
        }
    }

}
