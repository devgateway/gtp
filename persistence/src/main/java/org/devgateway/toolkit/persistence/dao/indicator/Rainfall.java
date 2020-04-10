package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class Rainfall extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1461094545735514686L;

    @NotNull
    @Column(nullable = false)
    private Integer day;

    @NotNull
    @Column(nullable = false)
    private Double rain;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private DecadalRainfall decadalRainfall;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public DecadalRainfall getDecadalRainfall() {
        return decadalRainfall;
    }

    public void setDecadalRainfall(DecadalRainfall decadalRainfall) {
        this.decadalRainfall = decadalRainfall;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
