package org.devgateway.toolkit.persistence.dto.drysequence;

import org.devgateway.toolkit.persistence.dto.ChartConfig;

/**
 * @author Octavian Ciubotaru
 */
public class DrySequenceChart {

    private final ChartConfig config;
    private final DrySequenceChartFilter filter;
    private final DrySequenceChartData data;

    public DrySequenceChart(
            ChartConfig config,
            DrySequenceChartFilter filter,
            DrySequenceChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public ChartConfig getConfig() {
        return config;
    }

    public DrySequenceChartFilter getFilter() {
        return filter;
    }

    public DrySequenceChartData getData() {
        return data;
    }
}
