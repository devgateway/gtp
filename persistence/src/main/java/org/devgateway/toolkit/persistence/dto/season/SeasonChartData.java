package org.devgateway.toolkit.persistence.dto.season;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChartData {

    private final int yearStart;
    private final int yearEnd;

    private final int referenceYearStart;
    private final int referenceYearEnd;

    private final List<SeasonPrediction> predictions;

    public SeasonChartData(int yearStart, int yearEnd, int referenceYearStart, int referenceYearEnd,
            List<SeasonPrediction> predictions) {
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.referenceYearStart = referenceYearStart;
        this.referenceYearEnd = referenceYearEnd;
        this.predictions = predictions;
    }

    public int getYearStart() {
        return yearStart;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public Integer getReferenceYearStart() {
        return referenceYearStart;
    }

    public Integer getReferenceYearEnd() {
        return referenceYearEnd;
    }

    public List<SeasonPrediction> getPredictions() {
        return predictions;
    }
}
