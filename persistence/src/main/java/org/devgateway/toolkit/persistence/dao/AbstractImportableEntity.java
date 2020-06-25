package org.devgateway.toolkit.persistence.dao;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Octavian Ciubotaru
 */
@MappedSuperclass
public abstract class AbstractImportableEntity extends AbstractAuditableEntity {

    @JsonIgnore
    public abstract boolean isEmpty();
}
