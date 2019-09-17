package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ConsumptionDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface ConsumptionDatasetRepository extends BaseJpaRepository<ConsumptionDataset, Long> {
}
