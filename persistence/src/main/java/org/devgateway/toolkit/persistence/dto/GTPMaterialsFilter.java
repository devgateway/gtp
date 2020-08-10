package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Nadejda Mandrescu
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GTPMaterialsFilter {

    private final Long locationId;

    public GTPMaterialsFilter() {
        this.locationId = null;
    }

    public GTPMaterialsFilter(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return locationId;
    }
}
