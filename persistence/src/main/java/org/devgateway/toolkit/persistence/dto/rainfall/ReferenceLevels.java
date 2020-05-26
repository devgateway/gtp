package org.devgateway.toolkit.persistence.dto.rainfall;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class ReferenceLevels {

    private final int yearStart;

    private final int yearEnd;

    private final int referenceYearStart;

    private final int referenceYearEnd;

    private final List<MonthDecadalRainLevel> levels;

    public ReferenceLevels(int yearStart, int yearEnd, int referenceYearStart, int referenceYearEnd,
            List<MonthDecadalRainLevel> levels) {
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.referenceYearStart = referenceYearStart;
        this.referenceYearEnd = referenceYearEnd;
        this.levels = levels;
    }

    public int getYearStart() {
        return yearStart;
    }

    public int getYearEnd() {
        return yearEnd;
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
