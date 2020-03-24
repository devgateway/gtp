package org.devgateway.toolkit.persistence.service.ipar;

import java.io.Serializable;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface AbstractDatasetService<T extends AbstractAuditableEntity & Serializable>
        extends BaseJpaService<T> {

    String getETagForDump();
}
