package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class Partner extends AbstractAuditableEntity implements Serializable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String sector;

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Person focalPoint;

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Person getFocalPoint() {
        return focalPoint;
    }

    public void setFocalPoint(Person focalPoint) {
        this.focalPoint = focalPoint;
    }
}
