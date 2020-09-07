package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dto.CommonConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class SampleCommonData {

    private final List<Zone> zones;
    private final List<Region> regions;
    private final List<Department> departments;

    private final CommonConfig commonConfig;
    private final Region regionKedougou;
    private final Department departmentKedougou;

    public SampleCommonData() {

        Zone zoneEst = new Zone(1L);
        zoneEst.setName("Zone Est");

        zones = new ArrayList<>();
        zones.add(zoneEst);

        regionKedougou = new Region(1L);
        regionKedougou.setZone(zoneEst);
        regionKedougou.setCode("KG");
        regionKedougou.setName("Kédougou");

        regions = new ArrayList<>();
        regions.add(regionKedougou);

        departmentKedougou = new Department(1L);
        departmentKedougou.setName("Kédougou");
        departmentKedougou.setCode("KG");
        departmentKedougou.setRegion(regionKedougou);

        departments = new ArrayList<>();
        departments.add(departmentKedougou);

        commonConfig = new CommonConfig(departments, regions, zones);
    }

    public List<Zone> getZones() {
        return zones;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Region getRegionKedougou() {
        return regionKedougou;
    }

    public Department getDepartmentKedougou() {
        return departmentKedougou;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }
}
