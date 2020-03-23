package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.GisIndicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface GisIndicatorRepository extends CategoryRepository<GisIndicator> {

    Page<GisIndicator> findByLabel(String term, Pageable page);
}
