package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Created by Daniel Oliva
 */
@MappedSuperclass
public abstract class Data extends AbstractAuditableEntity {

    @JsonIgnore
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
