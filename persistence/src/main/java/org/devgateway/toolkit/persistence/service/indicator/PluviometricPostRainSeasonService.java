package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.status.RainSeasonYearProgress;

/**
 * @author Nadejda Mandrescu
 */
public interface PluviometricPostRainSeasonService extends YearIndicatorGenerator<PluviometricPostRainSeason, Integer> {

    RainSeasonYearProgress getProgress(Integer year);
}
