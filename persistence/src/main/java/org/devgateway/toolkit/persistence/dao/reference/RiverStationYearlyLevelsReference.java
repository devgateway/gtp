package org.devgateway.toolkit.persistence.dao.reference;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.converter.HydrologicalYearConverter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"station_id", "year"}))
@JsonIgnoreProperties({"id", "parent", "new"})
public class RiverStationYearlyLevelsReference extends AbstractImportableEntity
        implements IRiverStationYearlyLevels<RiverLevelReference> {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RiverStation station;

    @NotNull
    @Column(nullable = false)
    @Convert(converter = HydrologicalYearConverter.class)
    private HydrologicalYear year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "riverStationYearlyLevelsReference")
    @SortNatural
    private SortedSet<RiverLevelReference> levels = new TreeSet<>();

    public RiverStationYearlyLevelsReference() {
    }

    public RiverStationYearlyLevelsReference(RiverStation station, HydrologicalYear year) {
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

    @Override
    public SortedSet<RiverLevelReference> getLevels() {
        return levels;
    }

    public void setLevels(SortedSet<RiverLevelReference> levels) {
        this.levels = levels;
    }

    public void addLevel(RiverLevelReference level) {
        level.setRiverStationYearlyLevelsReference(this);
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
