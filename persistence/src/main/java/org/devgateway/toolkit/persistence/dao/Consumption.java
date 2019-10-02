package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonPropertyOrder({"year", "department", "cropType", "cropSubType", "householdSize", "dailyConsumption",
        "weeklyConsumption"})
public class Consumption extends Data implements Serializable {

    private static final long serialVersionUID = -3219250112046118104L;

    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    private Department department;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private CropType cropType;

    @ManyToOne(optional = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @Nullable
    private CropSubType cropSubType;

    @PivotTableField(hideInDragAndDrop = true)
    private Integer householdSize;

    @PivotTableField(hideInDragAndDrop = true)
    private Double dailyConsumption;

    @PivotTableField(hideInDragAndDrop = true)
    private Double weeklyConsumption;

    public Consumption() {
    }

    public Consumption(Integer year, Department department, Integer householdSize) {
        this.setYear(year);
        this.department = department;
        this.householdSize = householdSize;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public CropSubType getCropSubType() {
        return cropSubType;
    }

    public void setCropSubType(CropSubType cropSubType) {
        this.cropSubType = cropSubType;
    }

    public Integer getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(Integer householdSize) {
        this.householdSize = householdSize;
    }

    public Double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(Double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public Double getWeeklyConsumption() {
        return weeklyConsumption;
    }

    public void setWeeklyConsumption(Double weeklyConsumption) {
        this.weeklyConsumption = weeklyConsumption;
    }
}
