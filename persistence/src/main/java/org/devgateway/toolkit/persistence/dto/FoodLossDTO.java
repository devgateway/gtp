package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;

public class FoodLossDTO extends DataDTO {

    private String cropType;
    private String lossType;
    private Double avgPercentage;
    private Double avgKilograms;

    public FoodLossDTO(FoodLossIndicator foodLoss) {
        super(foodLoss);
        this.cropType = getStr(foodLoss.getCropType());
        this.lossType = getStr(foodLoss.getLossType());
        this.avgKilograms = foodLoss.getAvgKilograms();
        this.avgPercentage = foodLoss.getAvgPercentage();
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
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