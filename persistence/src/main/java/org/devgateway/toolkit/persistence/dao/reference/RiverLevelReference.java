package org.devgateway.toolkit.persistence.dao.reference;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"river_station_yearly_levels_reference_id", "monthDay"}))
@JsonIgnoreProperties({"id", "parent", "new"})
public class RiverLevelReference extends AbstractAuditableEntity implements IRiverLevel {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RiverStationYearlyLevelsReference riverStationYearlyLevelsReference;

    @NotNull
    @Column(columnDefinition = "varchar", length = 6)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay monthDay;

    @NotNull
    @Min(0)
    @Column(scale = 1, precision = 10)
    private BigDecimal level;

    public RiverLevelReference() {
    }

    public RiverLevelReference(MonthDay monthDay, BigDecimal level) {
        this.monthDay = monthDay;
        this.level = level;
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
}
