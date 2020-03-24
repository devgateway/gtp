package org.devgateway.toolkit.forms.wicket.components.table.filter.ipar;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.ipar.WebContent;
// import org.devgateway.toolkit.persistence.dao.WebContent_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class WebContentFilterState extends JpaFilterState<WebContent> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String title;

    @Override
    public Specification<WebContent> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(title)) {
                // predicates.add(cb.like(cb.lower(root.get(WebContent_.title)), "%" + title.toLowerCase() + "%"));
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
}
