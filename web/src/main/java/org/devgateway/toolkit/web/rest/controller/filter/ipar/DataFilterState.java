package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.ipar.Data;
// import org.devgateway.toolkit.persistence.dao.Data_;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
// import org.devgateway.toolkit.persistence.dao.Dataset_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Daniel Oliva
 */
public class DataFilterState<T extends Data> implements Serializable {

    private static final long serialVersionUID = 2241550275925712593L;
    public static final String YEAR = "YEAR";
    public static final String YEAR_COLUMN = "year";
    public static final String REGION_COLUMN = "region";
    public static final String GENDER_COLUMN = "gender";
    public static final String CROPTYPE_COLUMN = "cropType";

    private DefaultFilterPagingRequest filter;

    public DataFilterState(DefaultFilterPagingRequest filter) {
        this.filter = filter;
    }

    @JsonIgnore
    public Specification<T> getSpecification() {
        return (root, query, cb) -> cb.and();
    }

    protected void addDataIdPredicates(Root<T> root, CriteriaBuilder cb,
                                        List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getId(), "id");
        // TODO fix type mismatch
    }

    protected void addDatasetIdPredicates(Root<T> root, CriteriaBuilder cb,
                                       List<Predicate> predicates) {
        // addIntPredicates(root, cb, predicates, filter.getDatasetId(), Data_.DATASET);
        // TODO fix type mismatch
    }

    protected void addApprovedDatasets(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        // Join<T, Dataset> join = root.join(Data_.DATASET, JoinType.LEFT);
        // predicates.add(cb.and(cb.isTrue(join.get(Dataset_.APPROVED))));
    }

    protected void addStringPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                       TreeSet<String> values, String columnName) {
        if (values != null && values.size() > 0) {
            CriteriaBuilder.In<String> inClause = cb.in(root.get(columnName));
            for (String str:values) {
                inClause.value(str);
            }
            predicates.add(inClause);
        }
    }

    protected void addIntPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                    TreeSet<Integer> values, String columnName) {
        if (values != null && values.size() > 0) {
            List<Predicate> intPred = new ArrayList<>();
            for (Integer value:values) {
                intPred.add(cb.equal(root.get(columnName), value));
            }
            predicates.add(cb.or(intPred.toArray(new Predicate[intPred.size()])));
        }
    }

    protected void addYearPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getYear(), YEAR_COLUMN);
    }

    protected void addYearDatePredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates, String column) {
        if (filter.getYear() != null  && filter.getYear().size() > 0) {
            List<Predicate> yearPred = new ArrayList<>();
            for (Integer value:filter.getYear()) {
                yearPred.add(cb.equal(cb.function(YEAR, Integer.class, root.get(column)), value));
            }
            predicates.add(cb.or(yearPred.toArray(new Predicate[predicates.size()])));
        }
    }

    protected void addMinPredicate(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                   Double value, String columnName) {
        if (value != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(columnName), value));
        }
    }

    protected void addMaxPredicate(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                   Double value, String columnName) {
        if (value != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(columnName), value));
        }
    }

    protected void addRegionPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getRegion(), REGION_COLUMN);
    }


    protected void addGenderPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getGender(), GENDER_COLUMN); // TODO fix type mismatch
    }

    protected void addCropTypePredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getCrop(), CROPTYPE_COLUMN);
    }
}