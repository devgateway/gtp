package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.categories.RiverStation;

import java.time.Month;
import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationStatus {

    private final RiverStation station;

    private final Map<Month, DataEntryStatus> statusByMonth;

    public RiverStationStatus(RiverStation station,
            Map<Month, DataEntryStatus> statusByMonth) {
        this.station = station;
        this.statusByMonth = statusByMonth;
    }

    public RiverStation getStation() {
        return station;
    }

    public DataEntryStatus get(Month month) {
        return statusByMonth.get(month);
    }

    public Map<Month, DataEntryStatus> getStatusByMonth() {
        return statusByMonth;
    }
}
