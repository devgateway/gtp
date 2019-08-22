package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.DataSource;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface DataSourceRepository extends BaseJpaRepository<DataSource, Long> {
}
