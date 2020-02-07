package org.devgateway.toolkit.persistence.dto;

public class PovertyGisDTO {

    private Integer year;

    private String code;

    private Double value;

    private String source;

    public PovertyGisDTO() {
    }

    public PovertyGisDTO(Integer year, String code, Double value, String source) {
        this.year = year;
        this.code = code;
        this.value = value;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
