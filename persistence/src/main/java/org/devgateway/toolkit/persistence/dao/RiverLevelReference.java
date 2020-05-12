package org.devgateway.toolkit.persistence.dao;

import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.converter.MonthDayStringAttributeConverter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 1000)
public class RiverLevelReference extends AbstractAuditableEntity implements Comparable<RiverLevelReference> {

    private static final Comparator<RiverLevelReference> HYDROLOGICAL =
            Comparator.comparing(RiverLevelReference::getMonthDay,
                    HydrologicalYear.HYDROLOGICAL_MONTH_DAY_COMPARATOR);

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    private RiverStationYearlyLevelsReference riverStationYearlyLevelsReference;

    @Column(columnDefinition = "varchar", length = 6, nullable = false)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay monthDay;

    @NotNull
    @Min(0)
    @Column(scale = 1, precision = 10, nullable = false)
    private BigDecimal level;

    public RiverLevelReference() {
    }

    public RiverLevelReference(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public RiverStationYearlyLevelsReference getRiverStationYearlyLevelsReference() {
        return riverStationYearlyLevelsReference;
    }

    public void setRiverStationYearlyLevelsReference(
            RiverStationYearlyLevelsReference riverStationYearlyLevelsReference) {
        this.riverStationYearlyLevelsReference = riverStationYearlyLevelsReference;
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public BigDecimal getLevel() {
        return level;
    }

    public void setLevel(BigDecimal level) {
        this.level = level;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return riverStationYearlyLevelsReference;
    }

    @Override
    public int compareTo(RiverLevelReference o) {
        return HYDROLOGICAL.compare(this, o);
    }
}
