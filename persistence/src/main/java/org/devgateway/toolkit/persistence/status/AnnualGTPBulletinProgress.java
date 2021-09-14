package org.devgateway.toolkit.persistence.status;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class AnnualGTPBulletinProgress extends DatasetProgress {

    private final List<DepartmentStatus> departmentStatuses;

    public AnnualGTPBulletinProgress(List<DepartmentStatus> departmentStatuses) {
        super("Rapports annuels GTP", "ANACIM");

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
