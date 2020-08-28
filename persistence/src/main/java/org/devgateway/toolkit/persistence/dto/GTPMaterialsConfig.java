package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.location.Department;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class GTPMaterialsConfig {
    private final List<GTPLocation> locations;

    public GTPMaterialsConfig(List<Department> locations) {
        this.locations = locations.stream().map(GTPLocation::new).collect(Collectors.toList());
    }

    public List<GTPLocation> getLocations() {
        return locations;
    }
}
