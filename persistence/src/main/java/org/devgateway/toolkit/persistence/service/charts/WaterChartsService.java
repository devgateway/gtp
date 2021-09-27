package org.devgateway.toolkit.persistence.service.charts;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dto.ChartConfig;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.WaterConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapConfig;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface WaterChartsService {

    ChartsData getCharts();

    WaterConfig getWaterConfig();

    RainLevelChartConfig getRainLevelConfig();

    RainLevelChartData getRainLevelData(RainLevelChartFilter filter);

    RainMapConfig getRainMapConfig();

    DecadalRainfallMap getRainMapData(RainMapFilter filter);

    SeasonChartConfig getRainSeasonConfig();

    SeasonChartData getRainSeasonData(SeasonChartFilter filter);

    DrySequenceChartData getDrySequenceData(DrySequenceChartFilter filter);

    ChartConfig getDrySequenceConfig();

    RiverLevelChartConfig getRiverLevelConfig();

    RiverLevelChartData getRiverLevelData(RiverLevelChartFilter filter);
}
