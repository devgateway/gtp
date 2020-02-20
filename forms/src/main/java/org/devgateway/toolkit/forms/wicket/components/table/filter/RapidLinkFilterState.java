package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.dao.RapidLink_;
import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition;
import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class RapidLinkFilterState extends JpaFilterState<RapidLink> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String title;
    private String titleFr;
    private String rapidLinkPosition;

    @Override
    public Specification<RapidLink> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(title)) {
                predicates.add(cb.like(root.get(RapidLink_.TITLE), "%" + title + "%"));
            }
            if (StringUtils.isNotBlank(titleFr)) {
                predicates.add(cb.like(root.get(RapidLink_.TITLE_FR), "%" + titleFr + "%"));
            }
            if (StringUtils.isNotBlank(rapidLinkPosition)) {
                Join<RapidLink, RapidLinkPosition> join = root.join(RapidLink_.RAPID_LINK_POSITION);
                predicates.add(cb.like(join.get(RapidLinkPosition_.LABEL), "%" + rapidLinkPosition + "%"));
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

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getRapidLinkPosition() {
        return rapidLinkPosition;
    }

    public void setRapidLinkPosition(String rapidLinkPosition) {
        this.rapidLinkPosition = rapidLinkPosition;
    }
}
