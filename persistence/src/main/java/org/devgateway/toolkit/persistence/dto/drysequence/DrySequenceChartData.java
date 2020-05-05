package org.devgateway.toolkit.persistence.dto.drysequence;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class DrySequenceChartData {

    private final List<MonthDecadalDaysWithRain> daysWithRain;

    public DrySequenceChartData(List<MonthDecadalDaysWithRain> daysWithRain) {
        this.daysWithRain = daysWithRain;
    }

    public List<MonthDecadalDaysWithRain> getDaysWithRain() {
        return daysWithRain;
    }
}
