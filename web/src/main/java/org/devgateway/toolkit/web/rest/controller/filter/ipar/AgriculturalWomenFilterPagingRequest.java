package org.devgateway.toolkit.web.rest.controller.filter.ipar;


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

    private TreeSet<Integer> ageGroup;

    private TreeSet<Integer> methodOfEnforcement;

    public AgriculturalWomenFilterPagingRequest() {
    }

    public AgriculturalWomenFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public AgriculturalWomenFilterPagingRequest(IndicatorFilterPagingRequest req) {
        super(req);
        this.minPercentage = req.getMinPercentage();
        this.maxPercentage = req.getMaxPercentage();
        this.awGroup = req.getAwGroup();
        this.awGroupType = req.getAwGroupType();
        this.ageGroup = req.getAgeGroup();
        this.methodOfEnforcement = req.getMethodOfEnforcement();
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
        TreeSet<Integer> all = new TreeSet<>();
        if (awGroupType != null) {
            all.addAll(awGroupType);
        }
        if (ageGroup != null) {
            all.addAll(ageGroup);
        }
        if (methodOfEnforcement != null) {
            all.addAll(methodOfEnforcement);
        }
        return all;
    }

    public void setAwGroupType(TreeSet<Integer> awGroupType) {
        this.awGroupType = awGroupType;
    }

    public TreeSet<Integer> getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(TreeSet<Integer> ageGroup) {
        this.ageGroup = ageGroup;
    }

    public TreeSet<Integer> getMethodOfEnforcement() {
        return methodOfEnforcement;
    }

    public void setMethodOfEnforcement(TreeSet<Integer> methodOfEnforcement) {
        this.methodOfEnforcement = methodOfEnforcement;
    }
}
