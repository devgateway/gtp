package org.devgateway.toolkit.web.rest.controller.filter;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TreeSet;

public class DatasetFilterPagingRequest extends GenericPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by text")
    private String text;

    @ApiModelProperty(value = "Filter by min date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date minDate;

    @ApiModelProperty(value = "Filter by max date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date maxDate;

    @ApiModelProperty(value = "Filter by organization")
    private TreeSet<Integer> organization;

    @ApiModelProperty(value = "Filter by year")
    private TreeSet<Integer> year;

    @ApiModelProperty(value = "Sort by column")
    private String sortBy;

    @ApiModelProperty(value = "Sort direction")
    private String sortDir;

    public DatasetFilterPagingRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public ZonedDateTime getRealMinDate() {
        return getRealDate(minDate);
    }

    public ZonedDateTime getRealMaxDate() {
        return getRealDate(maxDate);
    }

    public TreeSet<Integer> getOrganization() {
        return organization;
    }

    public void setOrganization(TreeSet<Integer> organization) {
        this.organization = organization;
    }

    public ZonedDateTime getRealDate(Date date) {
        if (date != null) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public TreeSet<Integer> getYear() {
        return year;
    }

    public void setYear(TreeSet<Integer> year) {
        this.year = year;
    }
}
