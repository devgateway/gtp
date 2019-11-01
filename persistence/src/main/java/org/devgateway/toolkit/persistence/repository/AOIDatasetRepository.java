package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface AOIDatasetRepository extends BaseJpaRepository<AgricultureOrientationIndexDataset, Long> {
}