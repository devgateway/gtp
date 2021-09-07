package org.devgateway.toolkit.persistence.status;

/**
 * @author Octavian Ciubotaru
 */
public class ProgressSummary {

    private final int noData;
    private final int draft;
    private final int published;

    public ProgressSummary(int noData, int draft, int published) {
        this.noData = noData;
        this.draft = draft;
        this.published = published;
    }

    public float getPublishedPerc() {
        return percOf(published);
    }

    public float getDraftPerc() {
        return percOf(draft);
    }

    public float getNoDataPerc() {
        return percOf(noData);
    }

    private float percOf(int value) {
        int total = noData + draft + published;
        return total == 0 ? 0f : ((float) value) / total;
    }
}
