package org.devgateway.toolkit.persistence.dto.rainfall;

/**
 * @author Octavian Ciubotaru
 */
public class RainLevelChart {

    private final RainLevelChartConfig config;
    private final RainLevelChartFilter filter;
    private final RainLevelChartData data;

    public RainLevelChart(RainLevelChartConfig config, RainLevelChartFilter filter,
            RainLevelChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public RainLevelChartConfig getConfig() {
        return config;
    }

    public RainLevelChartFilter getFilter() {
        return filter;
    }

    public RainLevelChartData getData() {
        return data;
    }
}
