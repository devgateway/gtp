package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class ReferenceLevels {

    private final int referenceYearStart;

    private final int referenceYearEnd;

    private final List<MonthDecadalRainLevel> levels;

    public ReferenceLevels(int referenceYearStart, int referenceYearEnd, List<MonthDecadalRainLevel> levels) {
        this.referenceYearStart = referenceYearStart;
        this.referenceYearEnd = referenceYearEnd;
        this.levels = levels;
    }

    public int getReferenceYearStart() {
        return referenceYearStart;
    }

    public int getReferenceYearEnd() {
        return referenceYearEnd;
    }

    public List<MonthDecadalRainLevel> getLevels() {
        return levels;
    }
}
