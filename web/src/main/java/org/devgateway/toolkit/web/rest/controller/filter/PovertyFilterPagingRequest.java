package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PovertyFilterPagingRequest extends DefaultFilterPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by minimum age")
    private Integer minAge;

    @ApiModelProperty(value = "Filter by maximum age")
    private Integer maxAge;

    @ApiModelProperty(value = "Filter by minimum poverty score")
    private Integer minScore;

    @ApiModelProperty(value = "Filter by maximum poverty score")
    private Integer maxScore;

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}
