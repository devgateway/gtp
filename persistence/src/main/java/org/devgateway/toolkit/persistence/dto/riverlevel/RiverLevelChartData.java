package org.devgateway.toolkit.persistence.dto.riverlevel;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelChartData {

    private final List<RiverStationYearlyLevels> yearlyLevels;

    private final List<RiverStationYearlyLevelsReference> referenceYearlyLevels;

    public RiverLevelChartData(
            List<RiverStationYearlyLevels> yearlyLevels,
            List<RiverStationYearlyLevelsReference> referenceYearlyLevels) {
        this.yearlyLevels = yearlyLevels;
        this.referenceYearlyLevels = referenceYearlyLevels;
    }

    public List<RiverStationYearlyLevels> getYearlyLevels() {
        return yearlyLevels;
    }

    public List<RiverStationYearlyLevelsReference> getReferenceYearlyLevels() {
        return referenceYearlyLevels;
    }
}
