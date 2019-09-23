package org.devgateway.toolkit.web.rest.controller.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Daniel Oliva
 */
public class DataFilterState<T extends Data> implements Serializable {

    private static final long serialVersionUID = 2241550275925712593L;
    public static final String YEAR = "YEAR";

    private DefaultFilterPagingRequest filter;

    public DataFilterState(DefaultFilterPagingRequest filter) {
        this.filter = filter;
    }

    @JsonIgnore
    public Specification<T> getSpecification() {
        return (root, query, cb) -> cb.and();
    }

    protected void addStringPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                       TreeSet<String> values, String columnName) {
        if (values != null) {
            CriteriaBuilder.In<String> inClause = cb.in(root.get(columnName));
            for (String str:values) {
                inClause.value(str);
            }
            predicates.add(inClause);
        }
    }

    protected void addIntPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                    TreeSet<Integer> values, String columnName) {
        if (values != null) {
            List<Predicate> intPred = new ArrayList<>();
            for (Integer value:values) {
                intPred.add(cb.equal(root.get(columnName), value));
            }
            predicates.add(cb.or(intPred.toArray(new Predicate[predicates.size()])));
        }
    }

    protected void addYearPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates,
                                    TreeSet<Integer> values, String columnName) {
        if (values != null) {
            List<Predicate> yearPred = new ArrayList<>();
            for (Integer value:values) {
                yearPred.add(cb.equal(cb.function(YEAR, Integer.class, root.get(columnName)), value));
            }
            predicates.add(cb.or(yearPred.toArray(new Predicate[predicates.size()])));
        }
    }
}
