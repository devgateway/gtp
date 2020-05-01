package org.devgateway.toolkit.persistence.dto.drysequence;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Octavian Ciubotaru
 */
public class DrySequenceChartFilter {

    @NotNull
    private final Integer year;

    @NotNull
    private final Long pluviometricPostId;

    public DrySequenceChartFilter(
            @JsonProperty("year") Integer year,
            @JsonProperty("pluviometricPostId") Long pluviometricPostId) {
        this.year = year;
        this.pluviometricPostId = pluviometricPostId;
    }

    public Integer getYear() {
        return year;
    }

    public Long getPluviometricPostId() {
        return pluviometricPostId;
    }
}
