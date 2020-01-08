package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dao.RegionIndicator_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class RegionIndicatorFilterState extends JpaFilterState<RegionIndicator> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String name;
    private String description;

    @Override
    public Specification<RegionIndicator> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(name)) {
                predicates.add(cb.like(cb.lower(root.get(RegionIndicator_.name)), "%" + name.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(description)) {
                predicates.add(cb.like(cb.lower(root.get(RegionIndicator_.description)), "%"
                        + description.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}