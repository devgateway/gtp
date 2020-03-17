package org.devgateway.toolkit.web.rest.controller.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Person_;
import org.devgateway.toolkit.persistence.dao.categories.DatasetType;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daniel Oliva
 */
public class DatasetFilterState {
    private static final long serialVersionUID = 8005371716983257722L;

    private DatasetFilterPagingRequest filter;
    private List<DatasetType> datasetTypes;

    public DatasetFilterState(DatasetFilterPagingRequest filter, List<DatasetType> datasetTypes) {
        this.filter = filter;
        this.datasetTypes = datasetTypes;
    }

    public Specification<Dataset> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDatePredicates(root, cb, predicates);
                addTextPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addOrganizationPredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            applyOrderByQuery(root, query);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private void addYearPredicates(Root<Dataset> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getYear() != null && filter.getYear().size() > 0) {
            List<Predicate> intPred = new ArrayList<>();
            for (Integer value:filter.getYear()) {
                intPred.add(cb.equal(root.get(Dataset_.YEAR), value));
            }
            predicates.add(cb.or(intPred.toArray(new Predicate[intPred.size()])));
        }
    }

    private void applyOrderByQuery(Root<Dataset> root, CriteriaQuery<?> query) {
        String column = "";
        if (StringUtils.isNotBlank(filter.getSortBy())) {
            column = filter.getSortBy().toLowerCase().trim();
        }
        boolean isAsc = false;
        if (filter.getSortDir() != null && filter.getSortDir().equalsIgnoreCase("asc")) {
            isAsc = true;
        }
        switch (column) {
            case "year" :
                query.orderBy(new OrderImpl(root.get(Dataset_.YEAR), isAsc));
                break;
            case "type" :
                query.orderBy(new OrderImpl(root.get(Dataset_.DTYPE), isAsc));
                break;
            case "title" :
                query.orderBy(new OrderImpl(root.get(Dataset_.LABEL), isAsc));
                break;
            case "organization" :
            case "org" :
                Join<Dataset, Organization> joinOrg = root.join(Dataset_.ORGANIZATION, JoinType.LEFT);
                query.orderBy(new OrderImpl(joinOrg.get(Organization_.LABEL), isAsc));
                break;
            case "creator" :
            case "createdby" :
                Join<Dataset, Person> joinPerson = root.join(Dataset_.CREATED_BY, JoinType.LEFT);
                query.orderBy(new OrderImpl(joinPerson.get(Person_.FIRST_NAME), isAsc));
                break;
            default :
                query.orderBy(new OrderImpl(root.get(Dataset_.CREATED_DATE), isAsc));
        }

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
            List<String> types = datasetTypes.stream().filter(x -> x.getLabel().toLowerCase().contains(txt)
                    || x.getLabelFr().toLowerCase().contains(txt))
                    .map(m -> m.getDescription())
                    .collect(Collectors.toList());
            if (types.size() > 0) {
                Expression<String> parentExpression = root.get(Dataset_.DTYPE);
                predicates.add(cb.and(cb.or(
                        cb.like(cb.lower(root.get(Dataset_.LABEL)), "%" + txt + "%"),
                        cb.like(cb.lower(root.get(Dataset_.SOURCE)), "%" + txt + "%"),
                        cb.like(cb.lower(root.get(Dataset_.CREATED_BY)), "%" + txt + "%"),
                        cb.like(cb.lower(join.get(Person_.FIRST_NAME)), "%" + txt + "%"),
                        cb.like(cb.lower(join.get(Person_.LAST_NAME)), "%" + txt + "%"),
                        parentExpression.in(types)
                )));
            } else {
                predicates.add(cb.and(cb.or(
                        cb.like(cb.lower(root.get(Dataset_.LABEL)), "%" + txt + "%"),
                        cb.like(cb.lower(root.get(Dataset_.SOURCE)), "%" + txt + "%"),
                        cb.like(cb.lower(root.get(Dataset_.CREATED_BY)), "%" + txt + "%"),
                        cb.like(cb.lower(join.get(Person_.FIRST_NAME)), "%" + txt + "%"),
                        cb.like(cb.lower(join.get(Person_.LAST_NAME)), "%" + txt + "%")
                )));
            }
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
