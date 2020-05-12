package org.devgateway.toolkit.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
public class RiverStationYearlyLevelsReference extends AbstractAuditableEntity {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    private RiverStation station;

    @NotNull
    @Column(nullable = false)
    @Convert(converter = HydrologicalYearConverter.class)
    private HydrologicalYear year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "riverStationYearlyLevelsReference")
    private List<RiverLevelReference> levels = new ArrayList<>();

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

    public List<RiverLevelReference> getLevels() {
        return levels;
    }

    public void setLevels(List<RiverLevelReference> levels) {
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
}
