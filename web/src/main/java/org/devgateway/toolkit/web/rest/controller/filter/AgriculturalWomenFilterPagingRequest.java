package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

public class AgriculturalWomenFilterPagingRequest extends DefaultFilterPagingRequest {

    @ApiModelProperty(value = "Filter by agricultural women group")
    private TreeSet<Integer> awGroup;

    @ApiModelProperty(value = "Filter by agricultural women group type")
    private TreeSet<Integer> awGroupType;

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
