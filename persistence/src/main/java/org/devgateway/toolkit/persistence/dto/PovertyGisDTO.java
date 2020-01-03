package org.devgateway.toolkit.persistence.dto;

public class PovertyGisDTO {

    private Integer year;

    private String code;

    private Double value;

    public PovertyGisDTO() {
    }

    public PovertyGisDTO(Integer year, String code, Double value) {
        this.year = year;
        this.code = code;
        this.value = value;
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
}
