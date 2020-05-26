package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;

/**
 * @author Octavian Ciubotaru
 */
public class ChartsData {

    private final CommonConfig commonConfig;

    private final WaterConfig waterConfig;

    private final RainLevelChart rainLevelChart;

    private final DrySequenceChart drySequenceChart;

    private final SeasonChart seasonChart;

    private final RiverLevelChart riverLevelChart;

    public ChartsData(CommonConfig commonConfig, WaterConfig waterConfig, RainLevelChart rainLevelChart,
            DrySequenceChart drySequenceChart,
            SeasonChart seasonChart, RiverLevelChart riverLevelChart) {
        this.commonConfig = commonConfig;
        this.waterConfig = waterConfig;
        this.rainLevelChart = rainLevelChart;
        this.drySequenceChart = drySequenceChart;
        this.seasonChart = seasonChart;
        this.riverLevelChart = riverLevelChart;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public WaterConfig getWaterConfig() {
        return waterConfig;
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

    public RiverLevelChart getRiverLevelChart() {
        return riverLevelChart;
    }
}
