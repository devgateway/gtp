package org.devgateway.toolkit.persistence.dao.categories;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class RiverStation extends AbstractAuditableEntity implements Labelable {
    private static final long serialVersionUID = 3087981614536825914L;

    public static final int MAX_RIVER_DEPTH_IN_CM = 10000;

    @ExcelExport(name = "river", useTranslation = true, justExport = true)
    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    private River river;

    @ExcelExport(name = "riverStationName", useTranslation = true)
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ExcelExport(name = "alertLevel", useTranslation = true)
    @NotNull
    @Min(0)
    @Max(MAX_RIVER_DEPTH_IN_CM)
    private Integer alertLevel;

    public RiverStation() {
    }

    public RiverStation(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public River getRiver() {
        return river;
    }

    public void setRiver(River river) {
        this.river = river;
    }

    public Integer getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(Integer alertLevel) {
        this.alertLevel = alertLevel;
    }

    @Override
    @JsonIgnore
    public String getLabel() {
        return name;
    }

    @Override
    public String getLabel(String lang) {
        return name;
    }

    @Override
    public void setLabel(String label) {
        this.name = label;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
