package org.devgateway.toolkit.persistence.dao.ipar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.envers.Audited;

/**
 * Describes a data source.
 *
 * @author Octavian Ciubotaru
 */
// @Entity
 @Audited
public class DataSource extends AbstractAuditableEntity {

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public DataSource() {
    }

    public DataSource(@NotEmpty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
