package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.categories.GisIndicator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
/**
 * Created by Daniel Oliva
 */
@Entity
@JsonIgnoreProperties({"new"})
public class GisSettingsDescription extends AbstractAuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private GisSettings gisSettings;

    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private GisIndicator gisIndicator;

    private String description;

    private String descriptionFr;

    public GisSettings getGisSettings() {
        return gisSettings;
    }

    public void setGisSettings(GisSettings gisSettings) {
        this.gisSettings = gisSettings;
    }

    public GisIndicator getGisIndicator() {
        return gisIndicator;
    }

    public void setGisIndicator(GisIndicator gisIndicator) {
        this.gisIndicator = gisIndicator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return gisSettings;
    }
}
