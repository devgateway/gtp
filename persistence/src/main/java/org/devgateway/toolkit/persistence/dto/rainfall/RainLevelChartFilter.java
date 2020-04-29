package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Octavian Ciubotaru
 */
public class RainLevelChartFilter {

    @NotNull
    @Size(min = 1, max = 3)
    private final List<@NotNull Integer> years;

    @NotNull
    private final Long pluviometricPostId;

    @JsonCreator
    public RainLevelChartFilter(
            @JsonProperty("years") List<Integer> years,
            @JsonProperty("pluviometricPostId") Long pluviometricPostId) {
        this.years = years;
        this.pluviometricPostId = pluviometricPostId;
    }

    public List<Integer> getYears() {
        return years;
    }

    public Long getPluviometricPostId() {
        return pluviometricPostId;
    }
}
