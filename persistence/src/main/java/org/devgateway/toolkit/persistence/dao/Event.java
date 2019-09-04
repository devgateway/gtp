package org.devgateway.toolkit.persistence.dao;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Daniel Oliva
 */
public class Event extends AbstractAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Dataset dataset;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
