package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.ipar.PovertyIndicator;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class PovertyDTO extends DataDTO {


    @ExcelExport(useTranslation = true)
    private String region;

    private String regionCode;

    @ExcelExport(useTranslation = true)
    private String locationType;

    @ExcelExport(useTranslation = true)
    private String gender;

    @ExcelExport(useTranslation = true)
    private String professionalActivity;

    @ExcelExport(useTranslation = true)
    private String povertyLevel;

    @ExcelExport(useTranslation = true)
    private Integer age;

    @ExcelExport(useTranslation = true)
    private Double povertyScore;

    public PovertyDTO(final PovertyIndicator p, final String lang) {
        super(p, lang);
        this.region = getStr(p.getRegion());
        this.regionCode = p.getRegion().getCode();
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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
