package org.devgateway.toolkit.persistence.dto.drysequence;

/**
 * @author Octavian Ciubotaru
 */
public class DrySequenceChart {

    private final DrySequenceChartFilter filter;
    private final DrySequenceChartData data;

    public DrySequenceChart(DrySequenceChartFilter filter,
            DrySequenceChartData data) {
        this.filter = filter;
        this.data = data;
    }

    public DrySequenceChartFilter getFilter() {
        return filter;
    }

    public DrySequenceChartData getData() {
        return data;
    }
}
