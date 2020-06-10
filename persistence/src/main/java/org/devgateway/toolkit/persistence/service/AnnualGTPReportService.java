package org.devgateway.toolkit.persistence.service;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportService extends BaseJpaService<AnnualGTPReport> {

    void generate();

    List<AnnualGTPReport> findAllWithUploads();
}
