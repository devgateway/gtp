package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportService extends BaseJpaService<AnnualGTPReport> {

    void generate();

    List<AnnualGTPReport> findAllWithUploadsAndDepartment(Long locationId);
}
