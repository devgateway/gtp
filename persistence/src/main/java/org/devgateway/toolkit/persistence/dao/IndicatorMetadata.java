package org.devgateway.toolkit.persistence.dao;

import org.devgateway.toolkit.persistence.dao.categories.Indicator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class IndicatorMetadata extends AbstractAuditableEntity implements Serializable {

    private String intro;

    private String introFr;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Indicator indicator;

    private String ansdLink;

    private String source;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntroFr() {
        return introFr;
    }

    public void setIntroFr(String introFr) {
        this.introFr = introFr;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public String getAnsdLink() {
        return ansdLink;
    }

    public void setAnsdLink(String ansdLink) {
        this.ansdLink = ansdLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
