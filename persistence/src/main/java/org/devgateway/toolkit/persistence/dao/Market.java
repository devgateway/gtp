package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "name"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class Market extends AbstractAuditableEntity implements Serializable, Labelable {

    @NotNull
    @ManyToOne(optional = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Department department;

    @NotNull
    @Column(nullable = false)
    private String name;

    public Market() {
    }

    public Market(Long id) {
        setId(id);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
}
