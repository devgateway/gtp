package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.ipar.FoodLossIndicator;

/**
 * Created by Daniel Oliva
 */
public class FoodLossSummary {

    private int year;
    private String cropType;
    private String cropTypeFr;
    private String lossType;
    private String lossTypeFr;
    private Double avgPercentage;
    private Double avgKilograms;

    public FoodLossSummary(FoodLossIndicator indicator) {
        this.year = indicator.getYear();
        this.cropType = indicator.getCropType().getLabel();
        this.cropTypeFr = indicator.getCropType().getLabelFr();
        this.lossType = indicator.getLossType().getLabel();
        this.lossTypeFr = indicator.getLossType().getLabelFr();
        this.avgPercentage = indicator.getAvgPercentage();
        this.avgKilograms = indicator.getAvgKilograms();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropTypeFr() {
        return cropTypeFr;
    }

    public void setCropTypeFr(String cropTypeFr) {
        this.cropTypeFr = cropTypeFr;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getLossTypeFr() {
        return lossTypeFr;
    }

    public void setLossTypeFr(String lossTypeFr) {
        this.lossTypeFr = lossTypeFr;
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
