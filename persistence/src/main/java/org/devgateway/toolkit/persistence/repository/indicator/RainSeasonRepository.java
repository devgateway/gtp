package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonRepository extends BaseJpaRepository<RainSeason, Long> {

    boolean existsByYear(Integer year);

    Optional<RainSeason> findByYear(Integer year);

    @Query("select s.year from RainSeason s "
            + "join s.postRainSeasons p "
            + "where p.formStatus = 'PUBLISHED'")
    List<Integer> findYearsWithData();
}
