package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Month;
import java.util.Comparator;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"month", "decadal", "rain_level_pluviometric_post_reference_id"}))
public class RainLevelMonthReference extends AbstractAuditableEntity implements Serializable,
        Comparable<RainLevelMonthReference> {
    private static final long serialVersionUID = 1017836457479552556L;

    private static final Comparator<RainLevelMonthReference> NATURAL = Comparator
            .comparing(RainLevelMonthReference::getMonth)
            .thenComparing(RainLevelMonthReference::getDecadal);

    public static final double MAX_RAIN = 10000;

    @NotNull
    @Column(nullable = false)
    private Month month;

    @NotNull
    @Column(nullable = false)
    private Decadal decadal;

    private Double rain;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RainLevelPluviometricPostReference rainLevelPluviometricPostReference;

    public RainLevelMonthReference() {
    }

    public RainLevelMonthReference(Month month, Decadal decadal, Double rain) {
        this.month = month;
        this.decadal = decadal;
        this.rain = rain;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public RainLevelPluviometricPostReference getRainLevelPluviometricPostReference() {
        return rainLevelPluviometricPostReference;
    }

    public void setRainLevelPluviometricPostReference(
            RainLevelPluviometricPostReference rainLevelPluviometricPostReference) {
        this.rainLevelPluviometricPostReference = rainLevelPluviometricPostReference;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(@NotNull RainLevelMonthReference rainLevelMonthReference) {
        return NATURAL.compare(this, rainLevelMonthReference);
    }
}
