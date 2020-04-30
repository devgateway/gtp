package org.devgateway.toolkit.persistence.dto.season;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChart {

    private final SeasonChartConfig config;
    private final SeasonChartFilter filter;
    private final SeasonChartData data;

    public SeasonChart(SeasonChartConfig config, SeasonChartFilter filter, SeasonChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public SeasonChartConfig getConfig() {
        return config;
    }

    public SeasonChartFilter getFilter() {
        return filter;
    }

    public SeasonChartData getData() {
        return data;
    }
}
