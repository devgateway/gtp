package org.devgateway.toolkit.persistence.repository.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonStartReferenceRepository extends BaseJpaRepository<RainSeasonStartReference, Long> {
    RainSeasonStartReference findByYearStartLessThanEqualAndYearEndGreaterThanEqual(Integer yearStart,
            Integer yearEnd);
}
