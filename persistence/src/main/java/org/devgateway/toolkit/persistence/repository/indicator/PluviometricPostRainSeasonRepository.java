package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface PluviometricPostRainSeasonRepository extends BaseJpaRepository<PluviometricPostRainSeason, Long> {
}