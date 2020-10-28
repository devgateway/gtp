package org.devgateway.toolkit.persistence.service.indicator.rainfallMap;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public interface DecadalRainfallMapService extends YearIndicatorGenerator<DecadalRainfallMap, Integer> {

    List<Integer> findYearsWithData();

    Month findLastMonthWithData(Integer year);

    Decadal findLastDecadalWithData(Integer year, Month month);

    DecadalRainfallMap findByYearAndMonthAndDecadal(Integer year, Month month, Decadal decadal);
}
