package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class YearlyRainfall extends AbstractStatusAuditableEntity implements AbstractImportableEntity {

    @NotNull
    @Column(nullable = false, unique = true)
    private Integer year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "yearlyRainfall")
    @JsonIgnore
    private List<StationDecadalRainfall> stationDecadalRainfalls = new ArrayList<>();

    public YearlyRainfall() {
    }

    public YearlyRainfall(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public List<StationDecadalRainfall> getStationDecadalRainfalls() {
        return stationDecadalRainfalls;
    }

    public void setStationDecadalRainfalls(final List<StationDecadalRainfall> stationDecadalRainfalls) {
        this.stationDecadalRainfalls = stationDecadalRainfalls;
    }

    public void addStationDecadalRainfall(StationDecadalRainfall stationDecadalRainfall) {
        stationDecadalRainfall.setYearlyRainfall(this);
        stationDecadalRainfalls.add(stationDecadalRainfall);
    }

    @Override
    public boolean isEmpty() {
        return stationDecadalRainfalls.isEmpty();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

}
