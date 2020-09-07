package org.devgateway.toolkit.persistence.service.charts;

import static java.util.stream.Collectors.toList;

import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private DepartmentService departmentService;

    @Override
    @Transactional(readOnly = true)
    public CommonConfig getCommonConfig() {
        List<Department> departments = departmentService.findAll();
        List<Region> regions = departments.stream().map(Department::getRegion).distinct().collect(toList());
        List<Zone> zones = regions.stream().map(Region::getZone).distinct().collect(toList());
        return new CommonConfig(departments, regions, zones);
    }
}
