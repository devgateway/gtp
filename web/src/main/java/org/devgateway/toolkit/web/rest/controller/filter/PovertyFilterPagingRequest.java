package org.devgateway.toolkit.web.rest.controller.filter;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.TreeSet;

public class PovertyFilterPagingRequest extends DefaultFilterPagingRequest implements Serializable {

    @ApiModelProperty(value = "Filter by minimum age")
    private Double minAge;

    @ApiModelProperty(value = "Filter by maximum age")
    private Double maxAge;

    @ApiModelProperty(value = "Filter by minimum poverty score")
    private Double minScore;

    @ApiModelProperty(value = "Filter by maximum poverty score")
    private Double maxScore;

    @ApiModelProperty(value = "Filter by poverty level")
    private TreeSet<Integer> povertyLevel;

    public PovertyFilterPagingRequest() {
    }

    public PovertyFilterPagingRequest(DefaultFilterPagingRequest req) {
        super(req);
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public TreeSet<Integer> getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(TreeSet<Integer> povertyLevel) {
        this.povertyLevel = povertyLevel;
    }
}
