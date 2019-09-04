package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class ProductionEvent extends Event implements Serializable {
    private static final long serialVersionUID = -3339250112046118104L;

    private String region;
    private Double crop1Surface;
    private Double crop1Production;
    private Double crop1Yield;
    private Double crop2Surface;
    private Double crop2Production;
    private Double crop2Yield;
    private Double crop3Surface;
    private Double crop3Production;
    private Double crop3Yield;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getCrop1Surface() {
        return crop1Surface;
    }

    public void setCrop1Surface(Double crop1Surface) {
        this.crop1Surface = crop1Surface;
    }

    public Double getCrop1Production() {
        return crop1Production;
    }

    public void setCrop1Production(Double crop1Production) {
        this.crop1Production = crop1Production;
    }

    public Double getCrop1Yield() {
        return crop1Yield;
    }

    public void setCrop1Yield(Double crop1Yield) {
        this.crop1Yield = crop1Yield;
    }

    public Double getCrop2Surface() {
        return crop2Surface;
    }

    public void setCrop2Surface(Double crop2Surface) {
        this.crop2Surface = crop2Surface;
    }

    public Double getCrop2Production() {
        return crop2Production;
    }

    public void setCrop2Production(Double crop2Production) {
        this.crop2Production = crop2Production;
    }

    public Double getCrop2Yield() {
        return crop2Yield;
    }

    public void setCrop2Yield(Double crop2Yield) {
        this.crop2Yield = crop2Yield;
    }

    public Double getCrop3Surface() {
        return crop3Surface;
    }

    public void setCrop3Surface(Double crop3Surface) {
        this.crop3Surface = crop3Surface;
    }

    public Double getCrop3Production() {
        return crop3Production;
    }

    public void setCrop3Production(Double crop3Production) {
        this.crop3Production = crop3Production;
    }

    public Double getCrop3Yield() {
        return crop3Yield;
    }

    public void setCrop3Yield(Double crop3Yield) {
        this.crop3Yield = crop3Yield;
    }
}
