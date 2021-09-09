package org.devgateway.toolkit.persistence.status;

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
        super("Cumul de pluie", "ANACIM");
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
