package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata_;
import org.devgateway.toolkit.persistence.dao.categories.Indicator;
import org.devgateway.toolkit.persistence.dao.categories.Indicator_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class IndicatorMetadataFilterState extends JpaFilterState<IndicatorMetadata> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String intro;

    @Override
    public Specification<IndicatorMetadata> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(intro)) {
                Join<IndicatorMetadata, Indicator> join = root.join(IndicatorMetadata_.indicator);
                predicates.add(cb.like(join.get(Indicator_.label), "%" + intro + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
