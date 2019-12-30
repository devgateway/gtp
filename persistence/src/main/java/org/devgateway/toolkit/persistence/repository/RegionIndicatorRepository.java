package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface RegionIndicatorRepository extends BaseJpaRepository<RegionIndicator, Long> {

}
