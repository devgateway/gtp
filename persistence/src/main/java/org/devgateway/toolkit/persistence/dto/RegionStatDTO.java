package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.RegionStat;

public class RegionStatDTO {

    private String regionCode;
    private Double value;

    public RegionStatDTO() {
    }

    public RegionStatDTO(RegionStat rs) {
        this.regionCode = rs.getRegionCode();
        this.value = rs.getValue();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
