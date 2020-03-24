package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Table;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Octavian Ciubotaru
 */
@NoRepositoryBean
public interface TableRepository extends BaseJpaRepository<Table, Long> {
}
