package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class RainfallYearProgress extends DatasetProgress {

    private final Map<Month, DataEntryStatus> statusByMonth;

    public RainfallYearProgress(Map<Month, DataEntryStatus> statusByMonth) {
        super(IndicatorType.RAINFALL);
        this.statusByMonth = statusByMonth;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return statusByMonth.values().stream();
    }

    public DataEntryStatus get(Month month) {
        return statusByMonth.get(month);
    }

    public Collection<DataEntryStatus> getStatuses() {
        return statusByMonth.values();
    }
}
