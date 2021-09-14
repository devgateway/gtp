package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.MonthDecadal;
import org.devgateway.toolkit.persistence.dao.location.Department;

import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public class DepartmentMonthDecadalStatus {

    private final Department department;

    private final Map<MonthDecadal, DataEntryStatus> statuses;

    public DepartmentMonthDecadalStatus(Department department,
            Map<MonthDecadal, DataEntryStatus> statuses) {
        this.department = department;
        this.statuses = statuses;
    }

    public Department getDepartment() {
        return department;
    }

    public Map<MonthDecadal, DataEntryStatus> getStatuses() {
        return statuses;
    }
}
