package org.devgateway.toolkit.persistence.dao;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Octavian Ciubotaru
 */
@MappedSuperclass
public abstract class AbstractChildAuditableEntity<P extends AbstractAuditableEntity>
        extends AbstractAuditableEntity {

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "parent_id")
    protected P parent;

    @Override
    public P getParent() {
        return parent;
    }

    public void setParent(P parent) {
        this.parent = parent;
    }
}
