package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.PovertyLevel;

public class PovertySummary {

    private long count;

    @JsonIgnore
    private long povertyLevelId;

    private String povertyLevel;

    private String povertyLevelFr;

    @JsonIgnore
    private long regionId;

    private String region;

    private String regionFr;

    private int year;

    private double percentage;

    public PovertySummary(Object[] objects) {
        if (objects != null && objects.length > 3) {
            this.count = (long) objects[0];
            PovertyLevel povertyLevel = (PovertyLevel) objects[1];
            this.povertyLevelId = povertyLevel.getId();
            this.povertyLevel = povertyLevel.getLabel();
            this.povertyLevelFr = povertyLevel.getLabelFr();
            this.year = (int) objects[2];
            Region region = (Region) objects[3];
            this.regionId = region.getId();
            this.region = region.getName();
            this.regionFr = region.getName();
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(String povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public String getPovertyLevelFr() {
        return povertyLevelFr;
    }

    public void setPovertyLevelFr(String povertyLevelFr) {
        this.povertyLevelFr = povertyLevelFr;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegionFr() {
        return regionFr;
    }

    public void setRegionFr(String regionFr) {
        this.regionFr = regionFr;
    }

    public long getPovertyLevelId() {
        return povertyLevelId;
    }

    public void setPovertyLevelId(long povertyLevelId) {
        this.povertyLevelId = povertyLevelId;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
