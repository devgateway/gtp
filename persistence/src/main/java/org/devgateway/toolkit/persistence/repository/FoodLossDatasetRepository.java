package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.FoodLossDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface FoodLossDatasetRepository
        extends BaseJpaRepository<FoodLossDataset, Long> {
}
