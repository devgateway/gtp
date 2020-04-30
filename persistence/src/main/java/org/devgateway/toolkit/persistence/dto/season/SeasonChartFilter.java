package org.devgateway.toolkit.persistence.dto.season;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChartFilter {

    @NotNull
    private final Integer year;

    public SeasonChartFilter(
            @JsonProperty("year") Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }
}
