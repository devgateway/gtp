package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;

/**
 * @author Octavian Ciubotaru
 */
public class ChartsData {

    private final CommonConfig commonConfig;

    private final RainLevelChart rainLevelChart;

    private final SeasonChart seasonChart;

    public ChartsData(CommonConfig commonConfig, RainLevelChart rainLevelChart, SeasonChart seasonChart) {
        this.commonConfig = commonConfig;
        this.rainLevelChart = rainLevelChart;
        this.seasonChart = seasonChart;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public RainLevelChart getRainLevelChart() {
        return rainLevelChart;
    }

    public SeasonChart getSeasonChart() {
        return seasonChart;
    }
}
