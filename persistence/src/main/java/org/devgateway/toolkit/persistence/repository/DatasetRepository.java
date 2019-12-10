package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface DatasetRepository extends BaseJpaRepository<Dataset, Long> {
}
