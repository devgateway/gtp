package org.devgateway.toolkit.persistence.dto;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;

/**
 * @author Octavian Ciubotaru
 */
public class CommonConfig {

    private final List<Department> departments;
    private final List<Region> regions;
    private final List<Zone> zones;

    public CommonConfig(List<Department> departments, List<Region> regions, List<Zone> zones) {
        this.departments = departments;
        this.regions = regions;
        this.zones = zones;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Zone> getZones() {
        return zones;
    }
}
