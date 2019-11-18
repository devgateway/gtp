package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class FoodLossFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by minimum percentage")
    private Double minAvgPercentage;

    @ApiModelProperty(value = "Filter by maximum percentage")
    private Double maxAvgPercentage;

    @ApiModelProperty(value = "Filter by minimum kilogram")
    private Double minAvgKilogram;

    @ApiModelProperty(value = "Filter by maximum kilogram")
    private Double maxAvgKilogram;

    @ApiModelProperty(value = "Filter by loss type")
    private TreeSet<Integer> lossType;

    public FoodLossFilterPagingRequest() {
    }

    public FoodLossFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public FoodLossFilterPagingRequest(IndicatorFilterPagingRequest req) {
        super(req);
        this.minAvgPercentage = req.getMinAvgPercentage();
        this.maxAvgPercentage = req.getMaxAvgPercentage();
        this.minAvgKilogram = req.getMinAvgKilogram();
        this.maxAvgKilogram = req.getMaxAvgKilogram();
        this.lossType = req.getLossType();
    }

    public TreeSet<Integer> getLossType() {
        return lossType;
    }

    public void setLossType(TreeSet<Integer> lossType) {
        this.lossType = lossType;
    }

    public Double getMinAvgPercentage() {
        return minAvgPercentage;
    }

    public void setMinAvgPercentage(Double minAvgPercentage) {
        this.minAvgPercentage = minAvgPercentage;
    }

    public Double getMaxAvgPercentage() {
        return maxAvgPercentage;
    }

    public void setMaxAvgPercentage(Double maxAvgPercentage) {
        this.maxAvgPercentage = maxAvgPercentage;
    }

    public Double getMinAvgKilogram() {
        return minAvgKilogram;
    }

    public void setMinAvgKilogram(Double minAvgKilogram) {
        this.minAvgKilogram = minAvgKilogram;
    }

    public Double getMaxAvgKilogram() {
        return maxAvgKilogram;
    }

    public void setMaxAvgKilogram(Double maxAvgKilogram) {
        this.maxAvgKilogram = maxAvgKilogram;
    }
}
