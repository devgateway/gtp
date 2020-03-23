package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;

public class AgriculturalWomenSummary {

    private int year;
    private String gender;
    private String genderFr;
    private String group;
    private String groupFr;
    private String groupType;
    private String groupTypeFr;
    private Double percentage;
    private Double utilizationPercentage;

    public AgriculturalWomenSummary(AgriculturalWomenIndicator womenIndicator) {
        this.year = womenIndicator.getYear();
        this.gender = womenIndicator.getGender().getLabel();
        this.genderFr = womenIndicator.getGender().getLabelFr();
        this.group = womenIndicator.getGroup().getLabel();
        this.groupFr = womenIndicator.getGroup().getLabelFr();
        this.groupType = womenIndicator.getGroupType().getLabel();
        this.groupTypeFr = womenIndicator.getGroupType().getLabelFr();
        this.percentage = womenIndicator.getPercentage();
        this.utilizationPercentage = womenIndicator.getUtilizationPercentage();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderFr() {
        return genderFr;
    }

    public void setGenderFr(String genderFr) {
        this.genderFr = genderFr;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupFr() {
        return groupFr;
    }

    public void setGroupFr(String groupFr) {
        this.groupFr = groupFr;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupTypeFr() {
        return groupTypeFr;
    }

    public void setGroupTypeFr(String groupTypeFr) {
        this.groupTypeFr = groupTypeFr;
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
