package org.devgateway.toolkit.persistence.dao.indicator;

import java.math.BigDecimal;
import java.time.MonthDay;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"river_station_yearly_levels_id", "monthDay"}))
public class RiverLevel extends AbstractAuditableEntity implements IRiverLevel {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    private RiverStationYearlyLevels riverStationYearlyLevels;

    @NotNull
    @Column(columnDefinition = "varchar", length = 6)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay monthDay;

    @NotNull
    @Min(0)
    @Column(scale = 1, precision = 10)
    private BigDecimal level;

    public RiverLevel() {
    }

    public RiverLevel(MonthDay monthDay, BigDecimal level) {
        this.monthDay = monthDay;
        this.level = level;
    }

    public RiverStationYearlyLevels getRiverStationYearlyLevels() {
        return riverStationYearlyLevels;
    }

    public void setRiverStationYearlyLevels(RiverStationYearlyLevels riverStationYearlyLevels) {
        this.riverStationYearlyLevels = riverStationYearlyLevels;
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
        return riverStationYearlyLevels;
    }
}
