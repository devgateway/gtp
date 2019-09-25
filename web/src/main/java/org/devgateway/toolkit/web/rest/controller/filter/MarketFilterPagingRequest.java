package org.devgateway.toolkit.web.rest.controller.filter;

import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class MarketFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by market id")
    private TreeSet<Integer> market;

    public TreeSet<Integer> getMarket() {
        return market;
    }

    public void setMarket(TreeSet<Integer> market) {
        this.market = market;
    }
}
