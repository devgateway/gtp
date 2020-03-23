package org.devgateway.toolkit.persistence.repository.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.IndexType;
import org.devgateway.toolkit.persistence.repository.category.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface IndexTypeRepository extends CategoryRepository<IndexType> {
}
