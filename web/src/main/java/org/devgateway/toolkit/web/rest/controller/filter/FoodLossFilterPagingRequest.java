package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class FoodLossFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by loss type")
    private TreeSet<Integer> lossType;

    public TreeSet<Integer> getLossType() {
        return lossType;
    }

    public void setLossType(TreeSet<Integer> lossType) {
        this.lossType = lossType;
    }
}
