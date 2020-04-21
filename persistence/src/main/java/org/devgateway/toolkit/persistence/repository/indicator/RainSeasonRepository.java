package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonRepository extends BaseJpaRepository<RainSeason, Long> {

    boolean existsByYear(Integer year);

    Optional<RainSeason> findByYear(Integer year);
}
