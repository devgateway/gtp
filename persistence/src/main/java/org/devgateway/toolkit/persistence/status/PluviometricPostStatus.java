package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;

/**
 * @author Octavian Ciubotaru
 */
public class PluviometricPostStatus {

    private final PluviometricPost post;

    private final DataEntryStatus status;

    public PluviometricPostStatus(PluviometricPost post, DataEntryStatus status) {
        this.post = post;
        this.status = status;
    }

    public DataEntryStatus getStatus() {
        return status;
    }

    public PluviometricPost getPost() {
        return post;
    }
}
