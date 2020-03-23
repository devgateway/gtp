package org.devgateway.toolkit.persistence.dao.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Entity
@JsonIgnoreProperties({"id", "new"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Data extends AbstractAuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    protected Dataset dataset;

    @PivotTableField(hideInAggregators = true)
    @Column(nullable = false)
    @NotNull
    @ExcelExport
    private Integer year;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
