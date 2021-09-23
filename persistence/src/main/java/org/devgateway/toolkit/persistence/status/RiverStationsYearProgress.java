package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationsYearProgress extends DatasetProgress {

    private final List<RiverStationStatus> riverStationStatuses;

    public RiverStationsYearProgress(List<RiverStationStatus> riverStationStatuses) {
        super(IndicatorType.RIVER_LEVEL);
        this.riverStationStatuses = riverStationStatuses;
    }

    public List<RiverStationStatus> getRiverStationStatuses() {
        return riverStationStatuses;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return riverStationStatuses.stream().flatMap(s -> s.getStatusByMonth().values().stream());
    }
}
