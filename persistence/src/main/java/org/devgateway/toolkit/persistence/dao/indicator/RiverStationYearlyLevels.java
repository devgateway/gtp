package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.converter.HydrologicalYearConverter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"station_id", "year"}))
@JsonIgnoreProperties({"id", "parent", "new"})
public class RiverStationYearlyLevels extends AbstractAuditableEntity
        implements AbstractImportableEntity, IRiverStationYearlyLevels<RiverLevel> {
    private static final long serialVersionUID = 8755815191828517219L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RiverStation station;

    @NotNull
    @Convert(converter = HydrologicalYearConverter.class)
    private HydrologicalYear year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "riverStationYearlyLevels")
    @SortNatural
    private SortedSet<RiverLevel> levels = new TreeSet<>();

    public RiverStationYearlyLevels() {
    }

    public RiverStationYearlyLevels(
            @NotNull RiverStation station,
            @NotNull HydrologicalYear year) {
        this.station = station;
        this.year = year;
    }

    @Override
    public RiverStation getStation() {
        return station;
    }

    @Override
    public void setStation(RiverStation station) {
        this.station = station;
    }

    @Override
    public HydrologicalYear getYear() {
        return year;
    }

    @Override
    public void setYear(HydrologicalYear year) {
        this.year = year;
    }

    @Override
    public SortedSet<RiverLevel> getLevels() {
        return levels;
    }

    public void setLevels(SortedSet<RiverLevel> levels) {
        this.levels = levels;
    }

    @Override
    public void addLevel(RiverLevel level) {
        level.setRiverStationYearlyLevels(this);
        levels.add(level);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return levels.isEmpty();
    }
}
