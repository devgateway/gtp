package org.devgateway.toolkit.persistence.status;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class GTPBulletinProgress extends DatasetProgress {

    private final List<DepartmentMonthDecadalStatus> departmentStatuses;

    public GTPBulletinProgress(List<DepartmentMonthDecadalStatus> departmentStatuses) {
        super("Bulletins GTP", "ANACIM");
        this.departmentStatuses = departmentStatuses;
    }

    public List<DepartmentMonthDecadalStatus> getDepartmentStatuses() {
        return departmentStatuses;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return departmentStatuses.stream().flatMap(s -> s.getStatuses().values().stream());
    }
}
