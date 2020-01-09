package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.categories.Indicator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.io.Serializable;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@JsonIgnoreProperties(value = { "new" })
public class IndicatorMetadata extends AbstractAuditableEntity implements Serializable {

    private String intro;

    private String introFr;

    @OneToOne(fetch = FetchType.LAZY)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonIgnore
    private Indicator indicator;

    @Column(length = 1024)
    private String ansdLink;

    private String source;

    public String getIntro() {
        return intro;
    }

    public String getIntro(String lang) {
        if (StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR)) {
            return introFr;
        }
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

    @JsonProperty("indicatorName")
    public String getIndicatorName() {
        return indicator != null ? indicator.getLabel() : null;
    }

    @JsonProperty("indicatorType")
    public Integer getIndicatorType() {
        return indicator != null ? indicator.getType() : null;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
