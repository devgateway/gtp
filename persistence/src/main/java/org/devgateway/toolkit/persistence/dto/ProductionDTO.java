package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Production;

public class ProductionDTO extends DataDTO {

    private String region;
    private String cropType;
    private Double surface;
    private Double production;
    private Double yield;

    public ProductionDTO(Production data) {
        super(data);
        this.region = getStr(data.getRegion());
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
