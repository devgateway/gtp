package org.devgateway.toolkit.web.rest.controller.filter;

import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class MarketFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by market name")
    private TreeSet<String> market;

    public TreeSet<String> getMarket() {
        return market;
    }

    public void setMarket(TreeSet<String> market) {
        this.market = market;
    }
}
