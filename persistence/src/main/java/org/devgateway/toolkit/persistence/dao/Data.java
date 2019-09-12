package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * Created by Daniel Oliva
 */
@MappedSuperclass
@JsonIgnoreProperties({"id", "new"})
public abstract class Data extends AbstractAuditableEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    protected Dataset dataset;
    protected String region;
    protected String crop;
    protected String campaign;
    protected LocalDate date;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
