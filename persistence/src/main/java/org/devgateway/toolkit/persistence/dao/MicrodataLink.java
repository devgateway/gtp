package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonIgnoreProperties({"id", "new"})
public class MicrodataLink extends AbstractAuditableEntity implements Serializable {

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
        String ret;
        if (link != null && link.length() > 30) {
            ret = link.substring(0, 29) + "...";
        } else {
            ret = link;
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

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
