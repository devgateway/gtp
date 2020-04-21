package org.devgateway.toolkit.persistence.repository.reference;

import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainSeasonPluviometricPostReferenceStartRepository
        extends BaseJpaRepository<RainSeasonPluviometricPostReferenceStart, Long> {
}
