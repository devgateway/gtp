package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.DepartmentStat;
import org.devgateway.toolkit.persistence.dao.RegionStat;

public class GisStatDTO {

    private String code;
    private Double value;

    public GisStatDTO() {
    }

    public GisStatDTO(RegionStat rs) {
        this.code = rs.getRegionCode();
        this.value = rs.getValue();
    }

    public GisStatDTO(DepartmentStat rs) {
        this.code = rs.getDepartmentCode();
        this.value = rs.getValue();
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
