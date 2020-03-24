package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class ConsumptionFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by crop subtype id")
    private TreeSet<Integer> cropSubType;

    public TreeSet<Integer> getCropSubType() {
        return cropSubType;
    }

    public void setCropSubType(TreeSet<Integer> cropSubType) {
        this.cropSubType = cropSubType;
    }
}
