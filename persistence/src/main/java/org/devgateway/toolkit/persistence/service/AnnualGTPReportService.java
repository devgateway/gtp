package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportService extends BaseJpaService<AnnualGTPReport> {

    void generate();
}
