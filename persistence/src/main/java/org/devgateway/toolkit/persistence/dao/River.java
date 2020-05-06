package org.devgateway.toolkit.persistence.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

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
public class River extends AbstractAuditableEntity {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
