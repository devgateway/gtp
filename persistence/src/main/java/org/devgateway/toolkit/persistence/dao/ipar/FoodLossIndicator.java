package org.devgateway.toolkit.persistence.dao.ipar;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.LossType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class FoodLossIndicator extends Data {

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private CropType cropType;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private LossType lossType;

    private Double avgPercentage;

    private Double avgKilograms;

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public Double getAvgPercentage() {
        return avgPercentage;
    }

    public void setAvgPercentage(Double avgPercentage) {
        this.avgPercentage = avgPercentage;
    }

    public Double getAvgKilograms() {
        return avgKilograms;
    }

    public void setAvgKilograms(Double avgKilograms) {
        this.avgKilograms = avgKilograms;
    }
}
