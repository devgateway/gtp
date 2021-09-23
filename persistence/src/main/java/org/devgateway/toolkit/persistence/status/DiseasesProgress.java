package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.time.Month;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class DiseasesProgress extends DatasetProgress {

    private final Map<Month, DataEntryStatus> statuses;

    public DiseasesProgress(Map<Month, DataEntryStatus> statuses) {
        super(IndicatorType.DISEASE_SITUATION);
        this.statuses = statuses;
    }

    public DataEntryStatus getStatus(Month month) {
        return statuses.get(month);
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return statuses.values().stream();
    }
}
