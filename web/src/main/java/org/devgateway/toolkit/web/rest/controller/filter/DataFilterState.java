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


    protected void addRegionPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getRegion() != null) {
            CriteriaBuilder.In<String> inClause = cb.in(root.get(Data_.REGION));
            for (String region:filter.getRegion()) {
                inClause.value(region);
            }
            predicates.add(inClause);
        }
    }

    protected void addYearPredicates(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getYear() != null) {
            List<Predicate> yearPred = new ArrayList<>();
            for (Integer year:filter.getYear()) {
                yearPred.add(cb.equal(cb.function(YEAR, Integer.class, root.get(Data_.DATE)), year));
            }
            predicates.add(cb.or(yearPred.toArray(new Predicate[predicates.size()])));
        }
    }
}
