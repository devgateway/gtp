package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.devgateway.toolkit.persistence.dao.location.Department;

/**
 * @author Nadejda Mandrescu
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GTPLocation {

    private final Long id;
    private final String name;

    public GTPLocation(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
