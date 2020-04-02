package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.WebContent;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface WebContentRepository extends BaseJpaRepository<WebContent, Long> {
}