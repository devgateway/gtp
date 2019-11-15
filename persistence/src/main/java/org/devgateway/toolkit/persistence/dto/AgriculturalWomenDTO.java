package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class AgriculturalWomenDTO extends DataDTO {


    @ExcelExport
    private String gender;

    @ExcelExport
    private String group;

    @ExcelExport
    private String groupType;

    @ExcelExport
    private Double percentage;

    @ExcelExport
    private Double utilizationPercentage;

    public AgriculturalWomenDTO(AgriculturalWomenIndicator women) {
        super(women);
        this.gender = getStr(women.getGender());
        this.group = getStr(women.getGroup());
        this.groupType = getStr(women.getGroupType());
        this.percentage = women.getPercentage();
        this.utilizationPercentage = women.getUtilizationPercentage();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
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
