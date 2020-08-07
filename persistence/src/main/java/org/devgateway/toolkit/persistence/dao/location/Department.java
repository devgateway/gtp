package org.devgateway.toolkit.persistence.dao.location;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class Department extends AbstractAuditableEntity implements Serializable, Labelable, Comparable<Department> {
    private static final long serialVersionUID = 5802901244304509439L;

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("regionId")
    private Region region;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String code;

    public Department() {
    }

    public Department(Long id) {
        setId(id);
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setLabel(String label) {
        this.name = label;
    }

    @Override
    @JsonIgnore
    public String getLabel() {
        return name;
    }

    @Override
    public String getLabel(String lang) {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(Department o) {
        if (o == null) {
            return -1;
        }
        return (o.getId() == null && getId() == null && o.getName().equals(getName()) || o.equals(this)) ? 0
                : getName().compareTo(o.getName());
    }

    public static int compareTo(Department a, Department b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a != null && b != null) {
            return a.compareTo(b);
        }
        if (a == null) {
            return -1;
        }
        return 1;
    }
}
