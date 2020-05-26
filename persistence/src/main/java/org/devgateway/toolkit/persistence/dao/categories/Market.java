package org.devgateway.toolkit.persistence.dao.categories;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "name"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class Market extends AbstractAuditableEntity implements Serializable, Labelable {

    @NotNull
    @ManyToOne(optional = false)
    private Department department;

    @NotNull
    @Column
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    private MarketType type;

    @NotNull @Min(1)
    private Integer marketDays = MarketDaysUtil.ALL_DAYS;

    @Min(12) @Max(17) @NotNull
    private Double latitude;

    @Min(-18) @Max(-11) @NotNull
    private Double longitude;

    public Market() {
    }

    public Market(Long id) {
        setId(id);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarketType getType() {
        return type;
    }

    public void setType(MarketType type) {
        this.type = type;
    }

    public Integer getMarketDays() {
        return marketDays;
    }

    public void setMarketDays(Integer marketDays) {
        this.marketDays = marketDays;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setLabel(String label) {
        this.name = label;
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
}