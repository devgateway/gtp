package org.devgateway.toolkit.persistence.dao.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Entity
@JsonIgnoreProperties({"id", "new"})
public class DepartmentStat extends GenericPersistable implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    protected DepartmentIndicator departmentIndicator;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private Department department;

    private Double value;

    public DepartmentIndicator getDepartmentIndicator() {
        return departmentIndicator;
    }

    public void setDepartmentIndicator(DepartmentIndicator departmentIndicator) {
        this.departmentIndicator = departmentIndicator;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @JsonProperty
    public String getDepartmentCode() {
        // return department.getCode();
        return null;
    }
}
