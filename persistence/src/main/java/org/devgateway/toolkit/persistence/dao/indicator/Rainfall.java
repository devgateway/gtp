package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class Rainfall extends AbstractAuditableEntity implements Comparable<Rainfall>, Serializable {
    private static final long serialVersionUID = 1461094545735514686L;

    private static final Comparator<Rainfall> NATURAL = Comparator.comparing(Rainfall::getDay);

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
    private PluviometricPostRainfall pluviometricPostRainfall;

    public Rainfall() {
    }

    public Rainfall(Integer day) {
        this(day, null);
    }

    public Rainfall(Integer day, Double rain) {
        this.day = day;
        this.rain = rain;
    }

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

    public PluviometricPostRainfall getPluviometricPostRainfall() {
        return pluviometricPostRainfall;
    }

    public void setPluviometricPostRainfall(PluviometricPostRainfall pluviometricPostRainfall) {
        this.pluviometricPostRainfall = pluviometricPostRainfall;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(Rainfall r) {
        return NATURAL.compare(this, r);
    }
}
