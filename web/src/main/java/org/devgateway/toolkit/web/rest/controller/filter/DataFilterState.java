package org.devgateway.toolkit.web.rest.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.dao.Data_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
            for (String region:values) {
                inClause.value(region);
            }
            predicates.add(inClause);
        }
    }
}
