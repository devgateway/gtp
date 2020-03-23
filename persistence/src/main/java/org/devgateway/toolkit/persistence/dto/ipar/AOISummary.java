package org.devgateway.toolkit.persistence.dto.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexIndicator;

public class AOISummary {

    private int year;
    private String indexType;
    private String indexTypeFr;
    private Double budgetedExpenditures;
    private Double disbursedExpenditures;
    private Double subsidies;
    private String variableType;
    private String variableTypeFr;


    public AOISummary(AgricultureOrientationIndexIndicator indicator) {
        this.year = indicator.getYear();
        this.indexType = indicator.getIndexType().getLabel();
        this.indexTypeFr = indicator.getIndexType().getLabelFr();
        this.budgetedExpenditures = indicator.getBudgetedExpenditures();
        this.disbursedExpenditures = indicator.getDisbursedExpenditures();
        this.subsidies = indicator.getSubsidies();
        this.variableType = indicator.getVariableType();
        this.variableTypeFr = indicator.getVariableType();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexTypeFr() {
        return indexTypeFr;
    }

    public void setIndexTypeFr(String indexTypeFr) {
        this.indexTypeFr = indexTypeFr;
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

    public String getVariableTypeFr() {
        return variableTypeFr;
    }

    public void setVariableTypeFr(String variableTypeFr) {
        this.variableTypeFr = variableTypeFr;
    }
}
