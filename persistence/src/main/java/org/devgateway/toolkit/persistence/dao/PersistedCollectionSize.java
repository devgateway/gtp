package org.devgateway.toolkit.persistence.dao;

/**
 * @author Octavian Ciubotaru
 */
public class PersistedCollectionSize {

    private final Long id;
    private final Long size;

    public PersistedCollectionSize(Long id, Long size) {
        this.id = id;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public Long getSize() {
        return size;
    }
}
