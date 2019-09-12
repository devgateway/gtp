package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.PovertyIndicatorDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface PovertyIndicatorDatasetRepository extends BaseJpaRepository<PovertyIndicatorDataset, Long> {
}
