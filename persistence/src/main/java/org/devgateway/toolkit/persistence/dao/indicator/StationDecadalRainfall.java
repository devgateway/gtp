package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.Month;
import java.time.YearMonth;
import java.util.Comparator;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"yearly_rainfall_id", "month", "decadal", "pluviometric_post_id"}))
public class StationDecadalRainfall extends AbstractAuditableEntity implements Comparable<StationDecadalRainfall> {
    private static final long serialVersionUID = -4244337786708697078L;

    public static final Comparator<StationDecadalRainfall> NATURAL =
            Comparator.comparing(StationDecadalRainfall::getPluviometricPost)
            .thenComparing(StationDecadalRainfall::getMonth)
            .thenComparing(StationDecadalRainfall::getDecadal);

    public static final double MAX_RAIN = 10000;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private YearlyRainfall yearlyRainfall;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    @NotNull
    @Column(nullable = false)
    private Month month;

    @NotNull
    @Column(nullable = false)
    private Decadal decadal;

    @NotNull
    @Column(nullable = false)
    private Double rainfall;

    @NotNull
    @Column(nullable = false)
    private Integer rainyDaysCount;

    public StationDecadalRainfall() {
    }

    public StationDecadalRainfall(PluviometricPost pluviometricPost, Month month, Decadal decadal) {
        this.pluviometricPost = pluviometricPost;
        this.month = month;
        this.decadal = decadal;
    }

    public YearlyRainfall getYearlyRainfall() {
        return yearlyRainfall;
    }

    public void setYearlyRainfall(final YearlyRainfall yearlyRainfall) {
        this.yearlyRainfall = yearlyRainfall;
    }

    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(final PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public MonthDTO getDisplayMonth() {
        return MonthDTO.of(month);
    }

    public int lengthOfMonth() {
        return MonthDTO.of(month).lengthOfMonth(yearlyRainfall.getYear());
    }

    public int lengthOfDecadal() {
        return decadal.length(YearMonth.of(yearlyRainfall.getYear(), month));
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }

    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(final Double rainfall) {
        this.rainfall = rainfall;
    }

    public Integer getRainyDaysCount() {
        return rainyDaysCount;
    }

    public void setRainyDaysCount(final Integer rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }

    @Override
    public int compareTo(StationDecadalRainfall stationDecadalRainfall) {
        return NATURAL.compare(this, stationDecadalRainfall);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
