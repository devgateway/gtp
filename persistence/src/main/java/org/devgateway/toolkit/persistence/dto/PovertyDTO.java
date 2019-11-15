package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class PovertyDTO extends DataDTO {


    @ExcelExport
    private String region;

    @ExcelExport
    private String locationType;

    @ExcelExport
    private String gender;

    @ExcelExport
    private String professionalActivity;

    @ExcelExport
    private String povertyLevel;

    @ExcelExport
    private Integer age;

    @ExcelExport
    private Double povertyScore;

    public PovertyDTO(PovertyIndicator p) {
        super(p);
        this.region = getStr(p.getRegion());
        this.locationType = getStr(p.getLocationType());
        this.gender = getStr(p.getGender());
        this.professionalActivity = getStr(p.getProfessionalActivity());
        this.povertyLevel = getStr(p.getPovertyLevel());
        this.age = p.getAge();
        this.povertyScore = p.getPovertyScore();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfessionalActivity() {
        return professionalActivity;
    }

    public void setProfessionalActivity(String professionalActivity) {
        this.professionalActivity = professionalActivity;
    }

    public String getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(String povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getPovertyScore() {
        return povertyScore;
    }

    public void setPovertyScore(Double povertyScore) {
        this.povertyScore = povertyScore;
    }
}
