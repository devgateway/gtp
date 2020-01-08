package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.ContentType;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface ContentTypeRepository extends CategoryRepository<ContentType> {
}
