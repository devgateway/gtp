package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;

/**
 * @author Octavian Ciubotaru
 */
public class ChartsData {

    private final CommonConfig commonConfig;

    private final RainLevelChart rainLevelChart;

    private final DrySequenceChart drySequenceChart;

    private final SeasonChart seasonChart;

    public ChartsData(CommonConfig commonConfig, RainLevelChart rainLevelChart,
            DrySequenceChart drySequenceChart,
            SeasonChart seasonChart) {
        this.commonConfig = commonConfig;
        this.rainLevelChart = rainLevelChart;
        this.drySequenceChart = drySequenceChart;
        this.seasonChart = seasonChart;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public RainLevelChart getRainLevelChart() {
        return rainLevelChart;
    }

    public DrySequenceChart getDrySequenceChart() {
        return drySequenceChart;
    }

    public SeasonChart getSeasonChart() {
        return seasonChart;
    }
}
