package org.devgateway.toolkit.web.rest.controller.filter;

import io.swagger.annotations.ApiModelProperty;

import java.util.TreeSet;

/**
 * Created by Daniel Oliva
 */
public class DefaultFilterPagingRequest extends GenericPagingRequest {

    public DefaultFilterPagingRequest() {
    }

    public DefaultFilterPagingRequest(DefaultFilterPagingRequest req) {
        if (req != null) {
            this.setId(req.getId());
            this.setDatasetId(req.getDatasetId());
            this.setCrop(req.getCrop());
            this.setRegion(req.getRegion());
            this.setYear(req.getYear());
            this.setGender(req.getGender());
            this.setPageNumber(req.getPageNumber());
            this.setPageSize(req.getPageSize());
        } else {
            this.setPageNumber(0);
            this.setPageSize(DEFAULT_PAGE_SIZE);
        }
    }

    @ApiModelProperty(value = "Filter by data id")
    private TreeSet<Integer> id;

    @ApiModelProperty(value = "Filter by dataset id")
    private TreeSet<Integer> datasetId;

    @ApiModelProperty(value = "Filter by region id")
    private TreeSet<Integer> region;

    @ApiModelProperty(value = "Filter by crop type id")
    private TreeSet<Integer> crop;

    @ApiModelProperty(value = "Filter by year")
    private TreeSet<Integer> year;

    @ApiModelProperty(value = "Filter by gender")
    private TreeSet<Integer> gender;

    @ApiModelProperty(value = "Language")
    private String lang;

    public TreeSet<Integer> getId() {
        return id;
    }

    public void setId(TreeSet<Integer> id) {
        this.id = id;
    }

    public TreeSet<Integer> getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(TreeSet<Integer> datasetId) {
        this.datasetId = datasetId;
    }

    public TreeSet<Integer> getRegion() {
        return region;
    }

    public void setRegion(TreeSet<Integer> region) {
        this.region = region;
    }

    public TreeSet<Integer> getCrop() {
        return crop;
    }

    public void setCrop(TreeSet<Integer> crop) {
        this.crop = crop;
    }

    public TreeSet<Integer> getYear() {
        return year;
    }

    public void setYear(TreeSet<Integer> year) {
        this.year = year;
    }

    public TreeSet<Integer> getGender() {
        return gender;
    }

    public void setGender(TreeSet<Integer> gender) {
        this.gender = gender;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
