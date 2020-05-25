package org.devgateway.toolkit.persistence.service.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;

/**
 * @author Nadejda Mandrescu
 */
public interface RainSeasonService extends YearIndicatorGenerator<RainSeason, Integer> {

    Collection<Integer> findYearsWithData();

    List<PluviometricPostRainSeason> findByYear(Integer year);
}
