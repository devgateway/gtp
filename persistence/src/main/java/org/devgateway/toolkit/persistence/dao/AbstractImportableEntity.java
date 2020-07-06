package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.MappedSuperclass;

/**
 * @author Octavian Ciubotaru
 */
@MappedSuperclass
public interface AbstractImportableEntity {

    @JsonIgnore
    boolean isEmpty();
}
