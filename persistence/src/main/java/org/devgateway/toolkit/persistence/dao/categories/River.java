package org.devgateway.toolkit.persistence.dao.categories;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
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
public class River extends AbstractAuditableEntity implements Labelable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public River() {
    }

    public River(Long id, String name) {
        setId(id);
        this.name = name;
    }

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
