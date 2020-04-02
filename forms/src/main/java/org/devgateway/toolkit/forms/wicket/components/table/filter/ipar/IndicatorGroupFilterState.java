package org.devgateway.toolkit.forms.wicket.components.table.filter.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndicatorGroup;
import org.springframework.data.jpa.domain.Specification;

// import org.devgateway.toolkit.persistence.dao.categories.IndicatorGroup_;
// import org.devgateway.toolkit.persistence.dao.categories.LocalizedCategoryLabel_;


/**
 * Created by dbianco
 */
public class IndicatorGroupFilterState extends JpaFilterState<IndicatorGroup> {

    private static final long serialVersionUID = 2241550275925712593L;


    @JsonIgnore
    public Specification<IndicatorGroup> getSpecification() {
        return (root, query, cb) -> {
            /*
            Join<IndicatorGroup, LocalizedCategoryLabel> join = root.join(IndicatorGroup_.LOCALIZED_LABELS);
            query.orderBy(cb.asc(join.get(LocalizedCategoryLabel_.LABEL)));
             */
            return cb.and();
        };
    }
}
