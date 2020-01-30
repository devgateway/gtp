package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.MicrodataLink;
import org.devgateway.toolkit.persistence.dao.MicrodataLink_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva.
 */
public class MicrodataLinkFilterState extends JpaFilterState<MicrodataLink> {

    private static final long serialVersionUID = 8005371716983257722L;
    private String title;
    private String organization;
    private String orgUser;
    private String description;
    private String descriptionFr;
    private String reducedDesc;
    private String reducedDescFr;

    public MicrodataLinkFilterState() {
    }

    public MicrodataLinkFilterState(String organization) {
        this.organization = organization;
    }

    @Override
    public Specification<MicrodataLink> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(title)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.title)), "%" + title.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(description)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.description)), "%"
                        + description.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(descriptionFr)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.descriptionFr)), "%"
                        + descriptionFr.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(reducedDesc)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.description)), "%"
                        + reducedDesc.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(reducedDescFr)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.descriptionFr)), "%"
                        + reducedDescFr.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(organization)) {
                Join<MicrodataLink, Organization> join = root.join(MicrodataLink_.organization);
                predicates.add(cb.like(cb.lower(join.get(Organization_.label)),
                        "%" + organization.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(orgUser)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.createdBy)), "%"
                        + descriptionFr.toLowerCase() + "%"));
            }
            query.orderBy(new OrderImpl(root.get(MicrodataLink_.createdDate), false));
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

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getReducedDesc() {
        return reducedDesc;
    }

    public void setReducedDesc(String reducedDesc) {
        this.reducedDesc = reducedDesc;
    }

    public String getReducedDescFr() {
        return reducedDescFr;
    }

    public void setReducedDescFr(String reducedDescFr) {
        this.reducedDescFr = reducedDescFr;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrgUser() {
        return orgUser;
    }

    public void setOrgUser(String orgUser) {
        this.orgUser = orgUser;
    }
}
