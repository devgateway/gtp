package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductionGisDTO {

    private Integer year;

    private String code;

    private Double value;

    @JsonIgnore
    private String crop;

    @JsonIgnore
    private String cropFr;

    private String source;

    public ProductionGisDTO() {
    }

    public ProductionGisDTO(Integer year, String code, Double value, String crop, String cropFr, String source) {
        this.year = year;
        this.code = code;
        this.value = value;
        this.crop = crop;
        this.cropFr = cropFr;
        this.source = source;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCropFr() {
        return cropFr;
    }

    public void setCropFr(String cropFr) {
        this.cropFr = cropFr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
