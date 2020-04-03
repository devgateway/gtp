package org.devgateway.toolkit.persistence.dao.indicator;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class IndicatorMetadata extends AbstractAuditableEntity implements Serializable {

    private String name;

    @Enumerated(EnumType.STRING)
    private IndicatorType type;

    private String intro;

    @Column(length = 1024)
    private String link;

    private String source;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IndicatorType getType() {
        return type;
    }

    public void setType(IndicatorType type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public String getIntro(String lang) {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
