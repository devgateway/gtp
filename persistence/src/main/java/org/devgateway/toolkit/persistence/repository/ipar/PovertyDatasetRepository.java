package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.PovertyDataset;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface PovertyDatasetRepository extends BaseJpaRepository<PovertyDataset, Long> {
}
