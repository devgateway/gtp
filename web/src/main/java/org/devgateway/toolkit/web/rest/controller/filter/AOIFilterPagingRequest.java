package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.TreeSet;

public class AOIFilterPagingRequest extends DefaultFilterPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by index type")
    private TreeSet<Integer> indexType = new TreeSet<>();

    public AOIFilterPagingRequest() {
    }

    public AOIFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public AOIFilterPagingRequest(IndicatorFilterPagingRequest req) {
        super(req);
        if (req.getIndexType() != null) {
            this.indexType.addAll(req.getIndexType());
        }
        if (req.getSubsidies().getIndexType() != null) {
            this.indexType.addAll(req.getSubsidies().getIndexType());
        }
        if (req.getBudget().getIndexType() != null) {
            this.indexType.addAll(req.getBudget().getIndexType());
        }
    }

    public TreeSet<Integer> getIndexType() {
        return indexType;
    }

    public void setIndexType(TreeSet<Integer> indexType) {
        this.indexType = indexType;
    }

    @Override
    public String toString() {
        return "AOI Filters{"
                + "year=" + getYear() + ","
                + "indexType=" + indexType
                + '}';
    }
}
