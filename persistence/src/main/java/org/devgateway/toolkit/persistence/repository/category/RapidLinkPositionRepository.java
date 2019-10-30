package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface RapidLinkPositionRepository extends CategoryRepository<RapidLinkPosition> {
}
