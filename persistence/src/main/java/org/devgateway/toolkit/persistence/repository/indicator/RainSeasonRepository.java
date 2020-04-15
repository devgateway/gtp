package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonRepository extends BaseJpaRepository<RainSeason, Long> {

    boolean existsByYear(Integer year);
}
