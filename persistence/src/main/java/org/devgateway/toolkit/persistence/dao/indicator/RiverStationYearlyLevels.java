package org.devgateway.toolkit.persistence.dao.indicator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.converter.HydrologicalYearConverter;
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
@BatchSize(size = 100)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"station_id", "year"}))
public class RiverStationYearlyLevels extends AbstractAuditableEntity implements IRiverStationYearlyLevels<RiverLevel> {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    private RiverStation station;

    @NotNull
    @Convert(converter = HydrologicalYearConverter.class)
    private HydrologicalYear year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "riverStationYearlyLevels")
    private List<RiverLevel> levels = new ArrayList<>();

    public RiverStationYearlyLevels() {
    }

    public RiverStationYearlyLevels(
            @NotNull RiverStation station,
            @NotNull HydrologicalYear year) {
        this.station = station;
        this.year = year;
    }

    public RiverStation getStation() {
        return station;
    }

    public void setStation(RiverStation station) {
        this.station = station;
    }

    public HydrologicalYear getYear() {
        return year;
    }

    public void setYear(HydrologicalYear year) {
        this.year = year;
    }

    public List<RiverLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<RiverLevel> levels) {
        this.levels = levels;
    }

    public void addLevel(RiverLevel level) {
        level.setRiverStationYearlyLevels(this);
        levels.add(level);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
