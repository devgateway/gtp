package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

/**
 * Created by Daniel Oliva
 */
public class FoodLossDTO extends DataDTO {

    @ExcelExport(useTranslation = true)
    private String cropType;

    @ExcelExport(useTranslation = true)
    private String lossType;

    @ExcelExport(useTranslation = true)
    private Double avgPercentage;

    @ExcelExport(useTranslation = true)
    private Double avgKilograms;

    public FoodLossDTO(FoodLossIndicator data, final String lang) {
        super(data, lang);
        this.cropType = getStr(data.getCropType());
        this.lossType = getStr(data.getLossType());
        this.avgKilograms = data.getAvgKilograms();
        this.avgPercentage = data.getAvgPercentage();
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
