package org.devgateway.toolkit.persistence.dto.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Consumption;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class ConsumptionDTO extends DataDTO {

    @ExcelExport
    private String region;

    @ExcelExport
    private String department;

    @ExcelExport
    private String cropType;

    @ExcelExport
    private String cropSubType;

    @ExcelExport
    private Integer householdSize;

    @ExcelExport
    private Double dailyConsumption;

    @ExcelExport
    private Double weeklyConsumption;

    public ConsumptionDTO(Consumption consumption, final String lang) {
        super(consumption, lang);
        this.region = getStr(consumption.getDepartment().getRegion());
        this.department = getStr(consumption.getDepartment());
        this.cropType = getStr(consumption.getCropType());
        this.cropSubType = getStr(consumption.getCropSubType());
        this.householdSize = consumption.getHouseholdSize();
        this.dailyConsumption = consumption.getDailyConsumption();
        this.weeklyConsumption = consumption.getWeeklyConsumption();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropSubType() {
        return cropSubType;
    }

    public void setCropSubType(String cropSubType) {
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
