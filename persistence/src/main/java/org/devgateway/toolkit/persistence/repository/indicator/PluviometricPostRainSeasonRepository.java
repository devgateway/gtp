package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface PluviometricPostRainSeasonRepository extends BaseJpaRepository<PluviometricPostRainSeason, Long> {

    @Override
    @CacheHibernateQueryResult
    Page<PluviometricPostRainSeason> findAll(Specification<PluviometricPostRainSeason> spec, Pageable pageable);
}
