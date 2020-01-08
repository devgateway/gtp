package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class AgriculturalContentFilterState extends JpaFilterState<AgriculturalContent> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String title;
    private String description;

    @Override
    public Specification<AgriculturalContent> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(title)) {
                predicates.add(cb.like(cb.lower(root.get(AgriculturalContent_.title)), "%"
                        + title.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(description)) {
                predicates.add(cb.like(cb.lower(root.get(AgriculturalContent_.description)), "%"
                        + description.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
