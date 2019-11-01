package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.TreeSet;

public class AOIFilterPagingRequest extends DefaultFilterPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by index type")
    private TreeSet<Integer> indexType;

    public AOIFilterPagingRequest() {
    }

    public AOIFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public TreeSet<Integer> getIndexType() {
        return indexType;
    }

    public void setIndexType(TreeSet<Integer> indexType) {
        this.indexType = indexType;
    }
}