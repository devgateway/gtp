package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;
import java.util.Set;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportService extends BaseJpaService<AnnualGTPReport> {

    Set<Department> findDepartments();

    void generate();

    List<AnnualGTPReport> findAllWithUploadsAndDepartment(Long locationId);
}
