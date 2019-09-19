package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class AgriculturalWomenIndicator extends Data {
    private static final long serialVersionUID = -3339250112046134104L;

    private Integer year;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private Gender gender;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private AgriculturalWomenGroup group;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private Category groupType;
    private Double percentage;
    private Double utilizationPercentage;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AgriculturalWomenGroup getGroup() {
        return group;
    }

    public void setGroup(AgriculturalWomenGroup group) {
        this.group = group;
    }

    public Category getGroupType() {
        return groupType;
    }

    public void setGroupType(Category groupType) {
        this.groupType = groupType;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(Double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }
}
