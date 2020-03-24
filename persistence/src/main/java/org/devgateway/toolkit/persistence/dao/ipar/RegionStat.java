package org.devgateway.toolkit.persistence.dao.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Entity
@JsonIgnoreProperties({"id", "new"})
public class RegionStat extends GenericPersistable implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    protected RegionIndicator regionIndicator;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private Region region;

    private Double value;

    public RegionIndicator getRegionIndicator() {
        return regionIndicator;
    }

    public void setRegionIndicator(RegionIndicator regionIndicator) {
        this.regionIndicator = regionIndicator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @JsonProperty
    public String getRegionCode() {
        return region.getCode();
    }
}
