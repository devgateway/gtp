package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface AOIDatasetRepository extends BaseJpaRepository<AgricultureOrientationIndexDataset, Long> {
}
