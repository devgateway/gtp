package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.MicrodataLink;
import org.devgateway.toolkit.persistence.dao.MicrodataLink_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.categories.Organization_;
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

    @Override
    public Specification<MicrodataLink> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(title)) {
                predicates.add(cb.like(cb.lower(root.get(MicrodataLink_.title)), "%" + title.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(organization)) {
                Join<MicrodataLink, Organization> join = root.join(MicrodataLink_.organization);
                predicates.add(cb.like(cb.lower(join.get(Organization_.label)),
                        "%" + organization.toLowerCase() + "%"));
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
