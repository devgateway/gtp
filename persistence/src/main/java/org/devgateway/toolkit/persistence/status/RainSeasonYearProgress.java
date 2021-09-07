package org.devgateway.toolkit.persistence.status;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class RainSeasonYearProgress extends DatasetProgress {

    private final List<PluviometricPostStatus> postStatuses;

    public RainSeasonYearProgress(List<PluviometricPostStatus> postStatuses) {
        super("Démarrage de la saison des pluies", "ANACIM");
        this.postStatuses = postStatuses;
    }

    public List<PluviometricPostStatus> getPostStatuses() {
        return postStatuses;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return postStatuses.stream().map(PluviometricPostStatus::getStatus);
    }
}
