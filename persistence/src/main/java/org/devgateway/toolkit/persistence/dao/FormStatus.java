package org.devgateway.toolkit.persistence.dao;

public enum FormStatus {
    NOT_STARTED,
    DRAFT,
    PUBLISHED;

    public boolean isPublished() {
        return this.equals(PUBLISHED);
    }
}
