package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.util.Collection;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public interface DecadalRainfallService extends YearIndicatorGenerator<DecadalRainfall, Integer> {

    List<Long> findPluviometricPostsWithData();

    List<Integer> findYearsWithData();

    List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId);

    List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId);

    void export(DecadalRainfall decadalRainfall, OutputStream outputStream) throws IOException;

    DecadalRainfall getExample(Integer year, Month month, Decadal decadal);
}
