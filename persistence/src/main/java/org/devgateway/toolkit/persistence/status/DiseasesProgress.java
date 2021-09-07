package org.devgateway.toolkit.persistence.status;

import java.time.Month;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class DiseasesProgress extends DatasetProgress {

    private final Map<Month, DataEntryStatus> statuses;

    public DiseasesProgress(Map<Month, DataEntryStatus> statuses) {
        super("Situation zoo sanitaire", "Ministère de l’élevage Direction de l’Elevage (DIREL)");
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
