package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent_;
import org.devgateway.toolkit.persistence.dao.categories.ContentType;
import org.devgateway.toolkit.persistence.dao.categories.ContentType_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class AgriculturalContentFilterState extends JpaFilterState<AgriculturalContent> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String reducedTitle;
    private String reducedTitleFr;
    private String publicationDateFormatted;
    private String contentType;

    @Override
    public Specification<AgriculturalContent> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(reducedTitle)) {
                predicates.add(cb.like(cb.lower(root.get(AgriculturalContent_.title)), "%"
                        + reducedTitle.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(reducedTitleFr)) {
                predicates.add(cb.like(cb.lower(root.get(AgriculturalContent_.titleFr)), "%"
                        + reducedTitleFr.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(contentType)) {
                Join<AgriculturalContent, ContentType> join = root.join(AgriculturalContent_.contentType);
                predicates.add(cb.like(join.get(ContentType_.label), "%" + contentType + "%"));
            }
            query.orderBy(new OrderImpl(root.get(AgriculturalContent_.publicationDate), false));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getReducedTitle() {
        return reducedTitle;
    }

    public void setReducedTitle(String reducedTitle) {
        this.reducedTitle = reducedTitle;
    }

    public String getReducedTitleFr() {
        return reducedTitleFr;
    }

    public void setReducedTitleFr(String reducedTitleFr) {
        this.reducedTitleFr = reducedTitleFr;
    }

    public String getPublicationDateFormatted() {
        return publicationDateFormatted;
    }

    public void setPublicationDateFormatted(String publicationDateFormatted) {
        this.publicationDateFormatted = publicationDateFormatted;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
