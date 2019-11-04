package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.categories.PovertyLevel;

public class PovertySummary {
    private long count;
    private String povertyLevel;
    private String povertyLevelFr;
    private String region;
    private String regionFr;
    private int year;

    public PovertySummary(Object[] objects) {
        if (objects != null && objects.length > 3) {
            this.count = (long) objects[0];
            PovertyLevel povertyLevel = (PovertyLevel) objects[1];
            this.povertyLevel = povertyLevel.getLabel();
            this.povertyLevelFr = povertyLevel.getLabelFr();
            this.year = (int) objects[2];
            this.region = objects[3].toString();
            this.regionFr = objects[3].toString();
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
}
