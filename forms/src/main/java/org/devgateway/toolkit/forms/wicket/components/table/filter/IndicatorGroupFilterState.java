package org.devgateway.toolkit.forms.wicket.components.table.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.categories.IndicatorGroup;
import org.devgateway.toolkit.persistence.dao.categories.IndicatorGroup_;
import org.devgateway.toolkit.persistence.dao.categories.LocalizedCategoryLabel;
import org.devgateway.toolkit.persistence.dao.categories.LocalizedCategoryLabel_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;


/**
 * Created by dbianco
 */
public class IndicatorGroupFilterState extends JpaFilterState<IndicatorGroup> {

    private static final long serialVersionUID = 2241550275925712593L;


    @JsonIgnore
    public Specification<IndicatorGroup> getSpecification() {
        return (root, query, cb) -> {
            Join<IndicatorGroup, LocalizedCategoryLabel> join = root.join(IndicatorGroup_.LOCALIZED_LABELS);
            query.orderBy(cb.asc(join.get(LocalizedCategoryLabel_.LABEL)));
            return cb.and();
        };
    }
}
