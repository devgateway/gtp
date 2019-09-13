package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@MappedSuperclass
@JsonIgnoreProperties({"id", "new"})
public abstract class Data extends AbstractAuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    protected Dataset dataset;

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
