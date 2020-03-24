package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.FoodLossDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface FoodLossDatasetRepository
        extends BaseJpaRepository<FoodLossDataset, Long> {
}
