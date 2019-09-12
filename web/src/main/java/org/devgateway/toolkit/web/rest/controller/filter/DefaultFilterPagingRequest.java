package org.devgateway.toolkit.web.rest.controller.filter;

import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

/**
 * Created by Daniel Oliva
 */
public class DefaultFilterPagingRequest extends GenericPagingRequest {

    @ApiModelProperty(value = "Filter by region name")
    private TreeSet<String> region;

    @ApiModelProperty(value = "Filter by crop name")
    private TreeSet<String> crop;

    @ApiModelProperty(value = "Filter by year")
    private TreeSet<Integer> year;

    public TreeSet<String> getRegion() {
        return region;
    }

    public void setRegion(TreeSet<String> region) {
        this.region = region;
    }

    public TreeSet<String> getCrop() {
        return crop;
    }

    public void setCrop(TreeSet<String> crop) {
        this.crop = crop;
    }

    public TreeSet<Integer> getYear() {
        return year;
    }

    public void setYear(TreeSet<Integer> year) {
        this.year = year;
    }
}
