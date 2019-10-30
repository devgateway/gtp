package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class AgricultureOrientationIndexIndicator extends Data {
    private static final long serialVersionUID = 3;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private IndexType indexType;

    private Double budgetedExpenditures;

    private Double disbursedExpenditures;

    private Double subsidies;

    private String variableType;

    public IndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }

    public Double getBudgetedExpenditures() {
        return budgetedExpenditures;
    }

    public void setBudgetedExpenditures(Double budgetedExpenditures) {
        this.budgetedExpenditures = budgetedExpenditures;
    }

    public Double getDisbursedExpenditures() {
        return disbursedExpenditures;
    }

    public void setDisbursedExpenditures(Double disbursedExpenditures) {
        this.disbursedExpenditures = disbursedExpenditures;
    }

    public Double getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Double subsidies) {
        this.subsidies = subsidies;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }
}
