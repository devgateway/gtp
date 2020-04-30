package org.devgateway.toolkit.persistence.service.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;

/**
 * @author Nadejda Mandrescu
 */
public interface DecadalRainfallService extends YearIndicatorGenerator<DecadalRainfall> {

    List<Long> findPluviometricPostsWithData();

    List<Integer> findYearsWithData();

    List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId);
}
