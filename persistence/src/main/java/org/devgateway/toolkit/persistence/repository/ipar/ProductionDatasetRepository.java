package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.ProductionDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface ProductionDatasetRepository extends BaseJpaRepository<ProductionDataset, Long> {
}
