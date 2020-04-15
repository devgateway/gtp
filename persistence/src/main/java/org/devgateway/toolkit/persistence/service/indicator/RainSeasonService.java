package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface RainSeasonService extends BaseJpaService<RainSeason> {

    boolean existsByYear(Integer year);

    void generate(Integer year);
}
