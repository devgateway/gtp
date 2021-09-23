package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class GTPAnnualReportProgress extends DatasetProgress {

    private final List<DepartmentStatus> departmentStatuses;

    public GTPAnnualReportProgress(List<DepartmentStatus> departmentStatuses) {
        super(IndicatorType.GTP_ANNUAL_REPORT);

        this.departmentStatuses = departmentStatuses;
    }

    public List<DepartmentStatus> getDepartmentStatuses() {
        return departmentStatuses;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return departmentStatuses.stream().map(DepartmentStatus::getStatus);
    }
}
