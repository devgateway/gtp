package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.NationalIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface NationalIndicatorRepository extends BaseJpaRepository<NationalIndicator, Long> {
}
