package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TreeSet;

public class DatasetFilterPagingRequest extends GenericPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by text")
    private String text;

    @ApiModelProperty(value = "Filter by min date")
    private String minDate;

    @ApiModelProperty(value = "Filter by max date")
    private String maxDate;

    @ApiModelProperty(value = "Filter by organization")
    private TreeSet<Integer> organization;

    @ApiModelProperty(value = "Languaje")
    private String lang;

    public DatasetFilterPagingRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
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

    public ZonedDateTime getRealDate(String dateStr) {
        //yyyy-MM-dd
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateStr);
                return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
