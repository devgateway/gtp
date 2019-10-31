package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class FoodLossFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by minimum percentage")
    private Double minPercentage;

    @ApiModelProperty(value = "Filter by maximum percentage")
    private Double maxPercentage;

    @ApiModelProperty(value = "Filter by minimum kilogram")
    private Double minKg;

    @ApiModelProperty(value = "Filter by maximum kilogram")
    private Double maxKg;

    @ApiModelProperty(value = "Filter by loss type")
    private TreeSet<Integer> lossType;

    public FoodLossFilterPagingRequest() {
    }

    public FoodLossFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public TreeSet<Integer> getLossType() {
        return lossType;
    }

    public void setLossType(TreeSet<Integer> lossType) {
        this.lossType = lossType;
    }

    public Double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }

    public Double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Double getMinKg() {
        return minKg;
    }

    public void setMinKg(Double minKg) {
        this.minKg = minKg;
    }

    public Double getMaxKg() {
        return maxKg;
    }

    public void setMaxKg(Double maxKg) {
        this.maxKg = maxKg;
    }
}
