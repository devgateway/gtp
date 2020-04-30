package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface ChartService {

    ChartsData getCharts();

    CommonConfig getCommonConfig();

    RainLevelChartConfig getRainLevelConfig();

    RainLevelChartData getRainLevelData(RainLevelChartFilter filter);

    SeasonChartConfig getRainSeasonConfig();

    SeasonChartData getRainSeasonData(SeasonChartFilter filter);
}
