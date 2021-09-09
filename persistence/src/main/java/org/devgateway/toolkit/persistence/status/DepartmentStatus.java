package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.location.Department;

/**
 * @author Octavian Ciubotaru
 */
public class DepartmentStatus {

    private final Department department;

    private final DataEntryStatus status;

    public DepartmentStatus(Department department, DataEntryStatus status) {
        this.department = department;
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public DataEntryStatus getStatus() {
        return status;
    }
}
