package org.devgateway.toolkit.persistence.service;

import java.io.Serializable;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;

/**
 * @author Octavian Ciubotaru
 */
public interface AbstractDatasetService<T extends AbstractAuditableEntity & Serializable>
        extends BaseJpaService<T> {

    String getETagForDump();
}
