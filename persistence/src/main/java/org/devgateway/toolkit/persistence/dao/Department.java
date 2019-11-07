package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class Department extends GenericPersistable implements Serializable, Labelable {

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Region region;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public Department() {
    }

    public Department(Long id) {
        setId(id);
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
    public String getLabel() {
        return name;
    }
}
