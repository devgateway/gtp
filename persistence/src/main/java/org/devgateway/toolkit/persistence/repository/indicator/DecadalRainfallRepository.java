package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface DecadalRainfallRepository extends BaseJpaRepository<DecadalRainfall, Long> {

    boolean existsByYear(Integer year);
}
