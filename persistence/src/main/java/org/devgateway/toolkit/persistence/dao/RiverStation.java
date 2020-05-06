package org.devgateway.toolkit.persistence.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class RiverStation extends AbstractAuditableEntity implements Labelable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private River river;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public River getRiver() {
        return river;
    }

    public void setRiver(River river) {
        this.river = river;
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

    @Override
    public void setLabel(String label) {
        this.name = label;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
