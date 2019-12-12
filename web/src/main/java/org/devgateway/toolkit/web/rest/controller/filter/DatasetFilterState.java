package org.devgateway.toolkit.web.rest.controller.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Person_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
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
                addOrganizationPredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            query.orderBy(new OrderImpl(root.get(Dataset_.CREATED_DATE), false));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addOrganizationPredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getOrganization() != null && filter.getOrganization().size() > 0) {
            List<Predicate> intPred = new ArrayList<>();
            Join<Dataset, Organization> join = root.join(Dataset_.ORGANIZATION, JoinType.LEFT);
            for (Integer value:filter.getOrganization()) {
                intPred.add(cb.equal(join.get("id"), value));
            }
            predicates.add(cb.or(intPred.toArray(new Predicate[intPred.size()])));
        }
    }

    protected void addApprovedDatasets(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        predicates.add(cb.and(cb.isTrue(root.get(Dataset_.APPROVED))));
    }

    protected void addTextPredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (StringUtils.isNotBlank(filter.getText())) {
            String txt = filter.getText().toLowerCase();
            Join<Dataset, Person> join = root.join(Dataset_.UPLOADED_BY, JoinType.LEFT);
            predicates.add(cb.and(cb.or(
                    cb.like(cb.lower(root.get(Dataset_.LABEL)), "%" + txt + "%"),
                    cb.like(cb.lower(root.get(Dataset_.SOURCE)), "%" + txt + "%"),
                    cb.like(cb.lower(root.get(Dataset_.CREATED_BY)), "%" + txt + "%"),
                    cb.like(cb.lower(join.get(Person_.FIRST_NAME)), "%" + txt + "%"),
                    cb.like(cb.lower(join.get(Person_.LAST_NAME)), "%" + txt + "%")
            )));
        }
    }


    protected void addDatePredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        ZonedDateTime minDate = filter.getRealMinDate();
        if (minDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Dataset_.CREATED_DATE), minDate));
        }
        ZonedDateTime maxDate = filter.getRealMaxDate();
        if (filter.getMaxDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Dataset_.CREATED_DATE), maxDate));
        }
    }

}
