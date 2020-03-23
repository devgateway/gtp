package org.devgateway.toolkit.persistence.dto.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class ProductionDTO extends DataDTO {

    @ExcelExport
    private String region;

    @ExcelExport
    private String department;

    @ExcelExport
    private String cropType;

    @ExcelExport
    private Double surface;

    @ExcelExport
    private Double production;

    @ExcelExport
    private Double yield;

    public ProductionDTO(Production data, final String lang) {
        super(data, lang);
        if (data.getDepartment() != null) {
            this.region = getStr(data.getDepartment().getRegion());
            this.department = getStr(data.getDepartment());
        }
        this.cropType = getStr(data.getCropType());
        this.surface = data.getSurface();
        this.production = data.getProduction();
        this.yield = data.getYield();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getProduction() {
        return production;
    }

    public void setProduction(Double production) {
        this.production = production;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }
}
