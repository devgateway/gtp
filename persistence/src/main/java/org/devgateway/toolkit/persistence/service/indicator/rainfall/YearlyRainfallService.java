package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.status.RainfallYearProgress;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public interface YearlyRainfallService extends YearIndicatorGenerator<YearlyRainfall, Integer> {

    List<Long> findPluviometricPostsWithData();

    List<Integer> findYearsWithData();

    List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId);

    List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId);

    void export(YearlyRainfall decadalRainfall, OutputStream outputStream) throws IOException;

    YearlyRainfall getExample(Integer year);

    RainfallYearProgress getProgress(Integer year);
}
