package org.devgateway.toolkit.forms.wicket.components.table.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

/**
 * Created by Octavian on 01.07.2016.
 */
public class JpaFilterState<T> implements Serializable {

    private static final long serialVersionUID = 2241550275925712593L;

    private String orderBy;

    public JpaFilterState() {
    }

    public JpaFilterState(String orderBy) {
        this.orderBy = orderBy;
    }

    @JsonIgnore
    public Specification<T> getSpecification() {
        return (root, query, cb) -> {
            if (StringUtils.isNotBlank(orderBy)) {
                query.orderBy(new OrderImpl(root.get(orderBy), true));
            }
            return cb.and();
        };
    }
}
