package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Consumption;

public class ConsumptionDTO extends DataDTO {

    private String region;
    private String department;
    private String cropType;
    private String cropSubType;
    private Integer householdSize;
    private Double dailyConsumption;
    private Double weeklyConsumption;

    public ConsumptionDTO(Consumption consumption) {
        super(consumption);
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
