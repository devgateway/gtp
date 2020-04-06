package org.devgateway.toolkit.persistence.dao.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@BatchSize(size = 100)
public class Region extends AbstractAuditableEntity implements Serializable, Labelable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String code;

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Zone zone;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private List<Department> departments = new ArrayList<>();

    public Region() {
    }

    public Region(Long id) {
        setId(id);
    }

    public Region(Long id, String name, String code) {
        setId(id);
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setLabel(String label) {
        this.name = label;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public String getLabel(String lang) {
        return name;
    }

    @JsonProperty("labelFr")
    public String getLabelFr() {
        return name;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
