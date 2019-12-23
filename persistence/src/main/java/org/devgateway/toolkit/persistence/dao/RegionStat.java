package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.categories.Statistic;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonIgnoreProperties({"id", "new"})
public class RegionStat extends GenericPersistable implements Serializable {

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private Statistic statistic;

    private Integer year;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private Region region;

    private String value;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty
    public Long getRegionId() {
        return region.getId();
    }

    @JsonProperty
    public String getRegion() {
        return region.getLabel();
    }

    @JsonProperty
    public String getRegionFr() {
        return region.getLabel("fr");
    }

    @JsonProperty
    public String getRegionCode() {
        return region.getCode();
    }
}
