package org.devgateway.toolkit.persistence.dao.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Linkable;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Entity
@JsonIgnoreProperties({"id", "new"})
public class MicrodataLink extends AbstractAuditableEntity implements Serializable, Linkable {

    private String title;

    private String description;

    private String titleFr;

    private String descriptionFr;

    @Column(length = 1024)
    private String link;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

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

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getLink() {
        return link;
    }

    @JsonIgnore
    public String getReducedLink() {
        return getReducedProp(link);
    }

    @JsonIgnore
    public String getReducedDesc() {
        return getReducedProp(description);
    }

    @JsonIgnore
    public String getReducedDescFr() {
        return getReducedProp(descriptionFr);
    }

    private String getReducedProp(String prop) {
        String ret;
        if (prop != null && prop.length() > 20) {
            ret = prop.substring(0, 19) + "...";
        } else {
            ret = prop;
        }
        return ret;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JsonIgnore
    public String getOrgUser() {
        StringBuilder ret = new StringBuilder();
        if (organization != null) {
            ret.append(organization.getLabel() + "/");
        }
        ret.append(getCreatedBy().get());
        return ret.toString();
    }

    @JsonIgnore
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return getCreatedDate().get().format(formatter);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
