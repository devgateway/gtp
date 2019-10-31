package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class AgriculturalWomenFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by minimum percentage")
    private Double minPercentage;

    @ApiModelProperty(value = "Filter by maximum percentage")
    private Double maxPercentage;

    @ApiModelProperty(value = "Filter by agricultural women group")
    private TreeSet<Integer> awGroup;

    @ApiModelProperty(value = "Filter by agricultural women group type")
    private TreeSet<Integer> awGroupType;

    public AgriculturalWomenFilterPagingRequest() {
    }

    public AgriculturalWomenFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
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

    public TreeSet<Integer> getAwGroup() {
        return awGroup;
    }

    public void setAwGroup(TreeSet<Integer> awGroup) {
        this.awGroup = awGroup;
    }

    public TreeSet<Integer> getAwGroupType() {
        return awGroupType;
    }

    public void setAwGroupType(TreeSet<Integer> awGroupType) {
        this.awGroupType = awGroupType;
    }
}
