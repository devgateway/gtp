package org.devgateway.toolkit.persistence.repository.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.GisIndicator;
import org.devgateway.toolkit.persistence.repository.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface GisIndicatorRepository extends CategoryRepository<GisIndicator> {

    Page<GisIndicator> findByLabel(String term, Pageable page);
}
