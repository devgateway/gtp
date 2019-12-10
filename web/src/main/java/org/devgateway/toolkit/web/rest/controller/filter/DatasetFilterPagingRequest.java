package org.devgateway.toolkit.web.rest.controller.filter;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

public class DatasetFilterPagingRequest extends GenericPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by text")
    private String text;

    @ApiModelProperty(value = "Filter by min date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private ZonedDateTime minDate;

    @ApiModelProperty(value = "Filter by max date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private ZonedDateTime maxDate;

    public DatasetFilterPagingRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getMinDate() {
        return minDate;
    }

    public void setMinDate(ZonedDateTime minDate) {
        this.minDate = minDate;
    }

    public ZonedDateTime getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(ZonedDateTime maxDate) {
        this.maxDate = maxDate;
    }
}
