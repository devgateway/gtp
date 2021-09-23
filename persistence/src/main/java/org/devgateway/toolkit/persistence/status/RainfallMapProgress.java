package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.MonthDecadal;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class RainfallMapProgress extends DatasetProgress {

    private final Map<MonthDecadal, DataEntryStatus> statusByMonthDecadal; // 6 months x 3 decadals = 18 elements

    public RainfallMapProgress(Map<MonthDecadal, DataEntryStatus> statusByMonthDecadal) {
        super(IndicatorType.RAINFALL_MAP);
        this.statusByMonthDecadal = statusByMonthDecadal;
    }

    public DataEntryStatus get(MonthDecadal monthDecadal) {
        return statusByMonthDecadal.get(monthDecadal);
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return statusByMonthDecadal.values().stream();
    }
}
