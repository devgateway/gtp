package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface ChartService {

    ChartsData getCharts();

    RainLevelChartConfig getRainLevelConfig();

    RainLevelChartData getRainLevelData(RainLevelChartFilter filter);
}
