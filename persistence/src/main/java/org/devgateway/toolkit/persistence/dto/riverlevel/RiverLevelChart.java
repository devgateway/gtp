package org.devgateway.toolkit.persistence.dto.riverlevel;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelChart {

    private final RiverLevelChartConfig config;

    private final RiverLevelChartFilter filter;

    private final RiverLevelChartData data;

    public RiverLevelChart(RiverLevelChartConfig config,
            RiverLevelChartFilter filter, RiverLevelChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public RiverLevelChartConfig getConfig() {
        return config;
    }

    public RiverLevelChartFilter getFilter() {
        return filter;
    }

    public RiverLevelChartData getData() {
        return data;
    }
}
