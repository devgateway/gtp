package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class RainLevelChartData {

    private final List<DecadalInstantRainLevel> levels;

    private final List<ReferenceLevels> referenceLevels;

    public RainLevelChartData(List<DecadalInstantRainLevel> levels, List<ReferenceLevels> referenceLevels) {
        this.levels = levels;
        this.referenceLevels = referenceLevels;
    }

    public List<DecadalInstantRainLevel> getLevels() {
        return levels;
    }

    public List<ReferenceLevels> getReferenceLevels() {
        return referenceLevels;
    }
}
