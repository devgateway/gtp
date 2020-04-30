package org.devgateway.toolkit.persistence.dto.season;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonChartData {

    private final Integer referenceYearStart;
    private final Integer referenceYearEnd;

    private final List<SeasonPrediction> predictions;

    public SeasonChartData(Integer referenceYearStart, Integer referenceYearEnd, List<SeasonPrediction> predictions) {
        this.referenceYearStart = referenceYearStart;
        this.referenceYearEnd = referenceYearEnd;
        this.predictions = predictions;
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
